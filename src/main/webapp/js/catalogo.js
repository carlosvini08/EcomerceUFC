let carrinho = 0;

function adicionar(botao) {
    carrinho++;
    document.getElementById('cart-count').innerText = carrinho;
    botao.innerText = "Adicionado!";
    botao.classList.add("added");
    setTimeout(() => {
        botao.innerText = "Encomendar";
        botao.classList.remove("added");
    }, 2000);
}

function favoritar(botao) {
    botao.classList.toggle("active");
}
