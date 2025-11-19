function getProdutos(){
    return JSON.parse(localStorage.getItem("produtos")) || [];
}

function salvarProdutos(produtos){
    localStorage.setItem("produtos", JSON.stringify(produtos));
}

// Formartar GTIN
function formatarGTIN(gtin){
    gtin = gtin.replace(/\D/g, "");
    return gtin.padStart(13, "0");
}

// Atualizar tabela
function atualizarTabela(filtro = ""){
    const produtos = getProdutos().slice();
    const criterio = document.getElementById("ordenarPor") ? document.getElementById("ordenarPor").value : "id";
    const corpoTabela = document.querySelector("#tabela-produtos tbody");

    // Ordenações
    produtos.sort((a, b) =>{
        if(criterio === "id"){
            return a.id - b.id;
        }
        if(criterio === "nome"){
            return a.nome.localeCompare(b.nome);
        }
        if(criterio === "gtin"){
            return a.gtin.localeCompare(b.gtin);
        }
        return 0;
    });

    corpoTabela.innerHTML = "";

    produtos
        .filter(p => p.nome.toLowerCase().includes(filtro.toLowerCase()))
        .forEach(p =>{
        const row = document.createElement("tr");

        // GTIN
        const tdGTIN = document.createElement("td");
        tdGTIN.textContent = p.gtin;

        // Nome
        const tdNome = document.createElement("td");
        tdNome.textContent = p.nome;

        // Descrição
        const tdDescricao = document.createElement("td");
        tdDescricao.textContent = p.descricao;

        // Ações
        const tdAcoes = document.createElement("td");

        // Botão Editar
        const btnEditar = document.createElement("button");
        btnEditar.className = "editar";
        btnEditar.textContent = "Editar";
        btnEditar.addEventListener("click", () => editarProduto(p.id));

        // Botão Excluir
        const btnExcluir = document.createElement("button");
        btnExcluir.className = "excluir";
        btnExcluir.textContent = "Excluir";
        btnExcluir.addEventListener("click", () => excluirProduto(p.id));

        tdAcoes.appendChild(btnEditar);
        tdAcoes.appendChild(btnExcluir);

        row.appendChild(tdGTIN);
        row.appendChild(tdNome);
        row.appendChild(tdDescricao);
        row.appendChild(tdAcoes);

        corpoTabela.appendChild(row);
    });
}

const selOrdenar = document.getElementById("ordenarPor");
if(selOrdenar){
    selOrdenar.addEventListener("change", () => atualizarTabela(document.querySelector("#busca") ? document.querySelector("#busca").value : ""));
}

// Mostrar/ocultar formulário de adicionar
const btnAdicionar = document.getElementById("btn-adicionar");
if(btnAdicionar){
    btnAdicionar.addEventListener("click", function (){
        const form = document.getElementById("form-produto");
        form.classList.toggle("oculto");

        if(form.classList.contains("oculto")){
            this.textContent = "Adicionar Produto";
            form.reset();
            document.querySelector("#id").value = "";
        }else{
            this.textContent = "Cancelar";
            const formEditar = document.getElementById("form-editar-produto");
            if(formEditar){ 
                formEditar.classList.add("oculto");
            }
        }
    });
}

// Mostrar/ocultar campo de busca
const btnBuscar = document.getElementById("btn-buscar");
if(btnBuscar){
    btnBuscar.addEventListener("click", function (){
        const campoBusca = document.getElementById("busca");
        campoBusca.classList.toggle("oculto");

        if(campoBusca.classList.contains("oculto")){
            this.textContent = "Buscar Produto";
            campoBusca.value = "";
            atualizarTabela();
        }else{
            this.textContent = "Ocultar Busca";
            campoBusca.focus();
        }
    });
}

// Adicionar produto
const formAdd = document.querySelector("#form-produto");
if(formAdd){
    formAdd.addEventListener("submit", e =>{
        e.preventDefault();

        const gtin = formatarGTIN(document.querySelector("#gtin").value);
        const nome = document.querySelector("#nome").value.trim();
        const descricao = document.querySelector("#descricao").value.trim();

        let produtos = getProdutos();

        if(produtos.some(p => p.gtin === gtin)){
            alert("ERRO: Este GTIN já está cadastrado!");
            return;
        }

        // Cria novo ID
        const novoId = produtos.length > 0 ? produtos[produtos.length - 1].id + 1 : 1;

        // Adiciona produto
        produtos.push({ id: novoId, gtin, nome, descricao });

        salvarProdutos(produtos);
        atualizarTabela();

        // Limpa e fecha o formulário
        e.target.reset();
        document.querySelector("#id").value = "";
        formAdd.classList.add("oculto");

        if (btnAdicionar) {
            btnAdicionar.textContent = "Adicionar Produto";
        }

        alert("Produto cadastrado com sucesso!");
    });
}

// Editar produto
function editarProduto(id){
    const produtos = getProdutos();
    const produto = produtos.find(p => p.id === id);

    if(produto){
        // Oculta o formulário de adicionar
        const formAdd = document.getElementById("form-produto");
        if(formAdd){ 
            formAdd.classList.add("oculto");
        }
        if(btnAdicionar){ 
            btnAdicionar.textContent = "Adicionar Produto";
        }

        // Mostra formulário de edição
        const formEditar = document.getElementById("form-editar-produto");
        formEditar.classList.remove("oculto");

        // Preenche os campos
        document.getElementById("editar-id").value = produto.id;
        document.getElementById("editar-gtin").value = produto.gtin;
        document.getElementById("editar-gtin").disabled = true;
        document.getElementById("editar-nome").value = produto.nome;
        document.getElementById("editar-descricao").value = produto.descricao;
    }
}

document.addEventListener("DOMContentLoaded", () =>{
    atualizarTabela();

    const formEditar = document.getElementById("form-editar-produto");
    formEditar.addEventListener("submit", function (e){
        e.preventDefault();

        const id = parseInt(document.getElementById("editar-id").value);
        const nome = document.getElementById("editar-nome").value.trim();
        const descricao = document.getElementById("editar-descricao").value.trim();

        const produtos = getProdutos();
        const index = produtos.findIndex(p => p.id === id);

        if(index !== -1){
            // Mantém o GTIN original
            const gtinOriginal = produtos[index].gtin;

            produtos[index] = { 
                id, 
                gtin: gtinOriginal,
                nome, 
                descricao 
            };

            salvarProdutos(produtos);
            atualizarTabela();

            alert("Produto alterado com sucesso!");
        }

        formEditar.classList.add("oculto");
    });
});

document.getElementById("cancelar-edicao").addEventListener("click", () =>{
    document.getElementById("form-editar-produto").classList.add("oculto");

    const campoBusca = document.getElementById("campo-busca");
    if(campoBusca){
        campoBusca.classList.remove("oculto");
    }
    document.getElementById("form-editar-produto").reset();
    const btnEditarPrincipal = document.getElementById("btn-editar-produto");
    if(btnEditarPrincipal){
        btnEditarPrincipal.textContent = "Buscar Produto";
        delete btnEditarPrincipal.dataset.modo;
    }
});


// Excluir produto 
function excluirProduto(id){
    const confirmar = confirm("Tem certeza que deseja excluir este produto?");
    if(!confirmar){ 
        return;
    }

    const produtos = getProdutos().filter(p => p.id != id);
    salvarProdutos(produtos);
    atualizarTabela();

    alert("Produto excluído com sucesso!");
}

// Buscar produto
const inputBusca = document.querySelector("#busca");
if(inputBusca){
    inputBusca.addEventListener("input", e =>{
        atualizarTabela(e.target.value);
    });
}

// Inicializa a tabela
document.addEventListener("DOMContentLoaded", () =>{
    atualizarTabela();
});