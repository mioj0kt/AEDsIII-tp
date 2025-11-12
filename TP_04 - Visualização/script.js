function getProdutos(){
    return JSON.parse(localStorage.getItem("produtos")) || [];
}


function salvarProdutos(produtos){
    localStorage.setItem("produtos", JSON.stringify(produtos));
}

// Atualiza a tabela
function atualizarTabela(filtro = ""){
    const produtos = getProdutos().slice();
    const criterio = document.getElementById("ordenarPor") ? document.getElementById("ordenarPor").value : "id";
    const corpoTabela = document.querySelector("#tabela-produtos tbody");

    // Ordena de acordo com o critério selecionado
    produtos.sort((a, b) =>{
        if(criterio === "id"){
            return a.id - b.id;
        }else if(criterio === "categoria"){
            return a.categoria.localeCompare(b.categoria);
        }else if(criterio === "precoAsc"){
            return a.preco - b.preco;
        }else if(criterio === "precoDesc"){
            return b.preco - a.preco;
        }else{
            return 0;
        }
    });

    // Limpa tabela
    corpoTabela.innerHTML = "";

    // Filtra e preenche novamente
    produtos
        .filter(p => p.nome.toLowerCase().includes(filtro.toLowerCase()))
        .forEach(p => {
        const row = document.createElement("tr");

        // Cria células
        const tdId = document.createElement("td");
        tdId.textContent = p.id;

        const tdNome = document.createElement("td");
        tdNome.textContent = p.nome;

        const tdCategoria = document.createElement("td");
        tdCategoria.textContent = p.categoria;

        const tdPreco = document.createElement("td");
        tdPreco.textContent = `R$ ${p.preco.toFixed(2)}`;

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

        row.appendChild(tdId);
        row.appendChild(tdNome);
        row.appendChild(tdCategoria);
        row.appendChild(tdPreco);
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

        const id = document.querySelector("#id").value;
        const nome = document.querySelector("#nome").value.trim();
        const categoria = document.querySelector("#categoria").value.trim();
        const preco = parseFloat(document.querySelector("#preco").value);

        let produtos = getProdutos();

        if(id){
            const index = produtos.findIndex(p => p.id == id);
            if(index !== -1) produtos[index] = { 
                id: parseInt(id), nome, categoria, preco 
            };
        }else{
            const novoId = produtos.length > 0 ? produtos[produtos.length - 1].id + 1 : 1;
            produtos.push({ id: novoId, nome, categoria, preco });
        }

        salvarProdutos(produtos);
        atualizarTabela();
        e.target.reset();
        document.querySelector("#id").value = "";
        formAdd.classList.add("oculto");
        if(btnAdicionar){
            btnAdicionar.textContent = "Adicionar Produto";
        }
    });
}

// Editar produto
function editarProduto(id){
    const produtos = getProdutos();
    const produto = produtos.find(p => p.id === id);

    if(produto){
        const formAdd = document.getElementById("form-produto");
        if (formAdd){ 
            formAdd.classList.add("oculto");
        }
        if(btnAdicionar){ 
            btnAdicionar.textContent = "Adicionar Produto";
        }

        const formEditar = document.getElementById("form-editar-produto");
        formEditar.classList.remove("oculto");
        document.getElementById("editar-id").value = produto.id;
        document.getElementById("editar-nome").value = produto.nome;
        document.getElementById("editar-categoria").value = produto.categoria;
        document.getElementById("editar-preco").value = produto.preco;
    }
}

document.addEventListener("DOMContentLoaded", () =>{
    atualizarTabela();

    const formEditar = document.getElementById("form-editar-produto");
    formEditar.addEventListener("submit", function (e){
        e.preventDefault();

        const id = parseInt(document.getElementById("editar-id").value);
        const nome = document.getElementById("editar-nome").value.trim();
        const categoria = document.getElementById("editar-categoria").value.trim();
        const preco = parseFloat(document.getElementById("editar-preco").value);

        const produtos = getProdutos();
        const index = produtos.findIndex(p => p.id === id);

        if(index !== -1){
            produtos[index] = { id, nome, categoria, preco };
            salvarProdutos(produtos);
            atualizarTabela();

            alert("Produto alterado com sucesso!");
        }

            formEditar.classList.add("oculto");
    });
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