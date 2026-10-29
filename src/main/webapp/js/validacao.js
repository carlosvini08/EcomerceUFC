/*
 * Validação dos formulários no navegador.
 * Formulários com a classe "form-validado" só são enviados ao servidor quando
 * todos os campos estão corretos. Os mesmos critérios são conferidos de novo
 * no servidor (ValidacaoUsuario.java).
 */
(() => {
    'use strict';

    const EMAIL = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const LOGIN = /^[A-Za-z0-9_.]{3,50}$/;
    const SENHA_MINIMA = 6;

    // retorna a mensagem de erro do campo ou "" se estiver correto
    function erroDoCampo(campo, form) {
        const valor = campo.value.trim();

        if (campo.required && valor === '') {
            return 'Campo obrigatório';
        }
        if (valor === '') {
            // campo opcional vazio (ex.: nova senha na alteração de dados);
            // a confirmação ainda precisa bater com a senha
            if (campo.name === 'confirmacaoSenha' && form.elements.senha.value !== '') {
                return 'Confirme a nova senha';
            }
            return '';
        }
        if (campo.type === 'email' && !EMAIL.test(valor)) {
            return 'Informe um e-mail válido (ex.: cidadao@fronteira.com)';
        }
        // as regras de login/senha só valem nos formulários de cadastro
        if (form.action.endsWith('/Login')) {
            return '';
        }
        if (campo.name === 'login' && !LOGIN.test(valor)) {
            return 'De 3 a 50 caracteres: letras, números, ponto ou _';
        }
        if (campo.name === 'senha' && campo.value.length < SENHA_MINIMA) {
            return 'A senha deve ter pelo menos ' + SENHA_MINIMA + ' caracteres';
        }
        if (campo.name === 'confirmacaoSenha' && campo.value !== form.elements.senha.value) {
            return 'As senhas não conferem';
        }
        return '';
    }

    function validarCampo(campo, form) {
        const erro = erroDoCampo(campo, form);
        const aviso = campo.parentElement.querySelector('.erro-campo');
        campo.classList.toggle('invalido', erro !== '');
        campo.setAttribute('aria-invalid', erro !== '');
        if (aviso) {
            aviso.textContent = erro;
        }
        return erro === '';
    }

    document.querySelectorAll('form.form-validado').forEach(form => {
        const campos = Array.from(form.querySelectorAll('input:not([type=hidden])'));
        let tentouEnviar = false;

        form.addEventListener('submit', evento => {
            tentouEnviar = true;
            // valida todos os campos (sem parar no primeiro) para mostrar todos os erros
            const resultados = campos.map(campo => validarCampo(campo, form));
            if (resultados.includes(false)) {
                evento.preventDefault();
                campos[resultados.indexOf(false)].focus();
            }
        });

        // depois da primeira tentativa, revalida enquanto o usuário digita
        campos.forEach(campo => {
            campo.addEventListener('input', () => {
                if (tentouEnviar) {
                    campos.forEach(c => validarCampo(c, form));
                }
            });
        });
    });

    // formulários de ações irreversíveis pedem confirmação
    document.querySelectorAll('form[data-confirmar]').forEach(form => {
        form.addEventListener('submit', evento => {
            if (!confirm(form.dataset.confirmar)) {
                evento.preventDefault();
            }
        });
    });
})();
