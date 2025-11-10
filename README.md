# PresenteFácil 3.0
## Alunos participantes
* [Domynic Barros Lima](https://github.com/DomynicBl)
* [Felipe Rivetti Mizher](https://github.com/FelipeMizher)
* [Matheus Felipe Cavalcanti Xavier](https://github.com/mioj0kt)

## Professor responsável
* Marcos Andre Silveira Kutova

## 📝 Descrição do Sistema
PresenteFácil é um sistema de console robusto para gerenciamento de listas de presentes. Desenvolvido em Java, o projeto permite que usuários se cadastrem, criem múltiplas listas para diferentes ocasiões (aniversários, casamentos, etc.), adicionem produtos a elas e as compartilhem com amigos e familiares através de um código único.

O sistema foi construído sobre uma arquitetura MVC, com um sistema de persistência de dados próprio que utiliza arquivos binários, índices em **Hash Extensível** para acesso rápido e **Árvores B+** para gerenciar os relacionamentos complexos entre as entidades e um Índice Invertido (baseado em Árvore B+) para buscas por relevância (TF-IDF).

---

### ✨ Principais Funcionalidades

O sistema é dividido em três grandes áreas de gerenciamento:

#### **Para Usuários**
* **Autenticação Segura:** Cadastro e login de usuários com emails e senhas, são armazenados com **hash SHA-256**.
* **Gerenciamento de Conta:** Usuários podem alterar seus dados pessoais (nome, e-mail, pergunta/resposta secreta e senha).
* **Recuperação de Senha:** Um fluxo seguro de recuperação de senha via pergunta e resposta secreta está disponível em caso de esquecimento.

#### **Para Listas de Presentes**
* **CRUD Completo:** Criação, edição e exclusão de múltiplas listas por usuário.
* **Gerenciamento de Produtos na Lista:** Adição (com busca unificada), alteração de quantidade/observações e remoção de produtos.
* **Estrutura da Lista** Cada lista possui nome, descrição, data de criação, uma data limite (opcional) e um código de indentificação unico (**NanoID**).
* **Compartilhamento Fácil:** Geração automática de um código **NanoID** de 10 caracteres para cada lista.
* **Busca Global:** Qualquer pessoa pode buscar uma lista pelo seu código **NanoID**, visualizando o autor e todos os seus produtos.
* **Organização:** Listas são sempre exibidas em ordem alfabética para o usuário, sendo exibidos todos os produtos ativos, e posteriormente os inativos.

#### **Para Produtos**
* **Busca Unificada (GTIN-13 e Palavra-Chave):** Implementação de uma busca única que aceita um GTIN-13 (para busca direta) ou palavras-chave (para busca por relevância).
* **Busca por Relevância (TF-IDF):** Ao buscar por palavras-chave, os resultados são ordenados por relevância usando o algoritmo **TF-IDF**, que prioriza termos mais raros e relevantes no nome dos produtos.
* **CRUD de Produtos:** Cadastro de novos produtos e alteração de suas informações (nome e descrição).
* **Validação Rigorosa:** Cada produto deve ter um GTIN-13 de exatamente 13 dígitos numéricos (não validamos se o GTIN-13 realmente existe ou corresponde a um produto real).
* **Listagem Paginada:** Produtos são exibidos em páginas de 10 itens, com navegação intuitiva entre as páginas.
* **Ordenação Inteligente:** A listagem de produtos prioriza itens ativos e os ordena alfabeticamente. Produtos inativos são agrupados no final, também em ordem alfabética.
* **Relacionamento N:N:** Um produto pode ser adicionado a várias listas, e uma lista pode conter vários produtos.
* **Gestão na Lista:** Dentro de uma lista, é possível adicionar, remover e alterar a quantidade e observações de cada produto.
* **Inativação de Produtos:** Produtos podem ser inativados (ao invés de excluídos) e reativados. Produtos inativos são movidos para o final das listagens e não podem ser adicionados a novas listas, mas permanecem visíveis nas listas existentes.
* **Visualização Detalhada:** Ao selecionar um produto, exibe nome, GTIN-13, descrição, status, e em quais listas do usuário logado ele aparece, além da contagem de outras listas em que está presente.

---

### 🚀 Recursos Avançados e Regras de Negócio

Para garantir a robustez, usabilidade e integridade dos dados, implementamos diversas operações especiais:

* **Persistência e Indexação:**
    * O sistema utiliza um CRUD genérico sobre arquivos binários com **lápide** para reaproveitamento de espaço.
    * Índices em **Hash Extensível** garantem acesso rápido a usuários (por e-mail), produtos (por GTIN-13) e listas (por código compartilhável).
    * **Árvores B+** são utilizadas para gerenciar com eficiência os relacionamentos **1:N** (Usuário → Listas) e **N:N** (Listas ↔ Produtos).
    * Um **Índice Invertido** (implementado com `ArvoreBMais<ParTermoProduto>`) gerencia o relacionamento "1-Termo-para-N-Produtos", permitindo a busca por relevância (TF-IDF).

* **Integridade Referencial:**
    1. **Usuário → Lista:** Um usuário não pode ser excluído se possuir listas ativas, prevenindo listas "órfãs".
    2. **Lista → Produtos:** Ao excluir uma lista, todas as suas associações com produtos são removidas em cascata.
    3. **Inativação de Produtos:** Produtos não são excluídos fisicamente se estiverem em uso. Eles são **inativados**:
        * Continuam visíveis nas listas onde já estavam (com a marcação `(INATIVO)`).
        * Não podem ser adicionados a novas listas.
        * Podem ser reativados a qualquer momento pelo menu de gerenciamento de produtos.
    4. **Sincronização do Índice de Busca:** O índice invertido é atualizado automaticamente. Os termos de um produto são removidos ao inativar/excluir e recalculados ao criar ou **alterar o nome**, garantindo a consistência da busca.

* **Segurança e Usabilidade:**
    * **Confirmação de Alterações:** Toda alteração nos dados do usuário exige a confirmação da **resposta secreta atual**, garantindo que apenas o dono da conta possa modificar informações sensíveis.
    * **Validação de Entradas:** O cadastro de produtos exige um GTIN-13 com exatamente 13 dígitos numéricos e campos obrigatórios preenchidos.
    * **Cancelamento de Operações:** O usuário pode cancelar o cadastro de um produto a qualquer momento digitando 'R' em qualquer campo.
    * **Processamento de Texto:** Nomes de produtos são processados antes da indexação (normalizados, sem acentos, sem *stop words*) pela classe `ProcessadorTexto`.
    * **Estrutura de Arquivos:** A pasta `Dados/` é gerada dentro do diretório do projeto (`TP_0X/`), mantendo a organização.

---

## 🏗️ Estrutura do Projeto (Arquitetura MVC)

O projeto é organizado em pacotes que separam as responsabilidades, seguindo o padrão Model-View-Controller.

* **`Entidades` (Model):** Representa o núcleo do Modelo, contendo as classes que definem os objetos de negócio do sistema.
    * **`Usuario.java`**: Modela a entidade Usuário, com atributos como `ID, nome, e-mail, hash da senha, pergunta secreta, resposta secreta, etc.`.
    * **`Lista.java`**: Modela a entidade Lista, com atributos como `ID, ID do usuário, nome, descrição, código compartilhável, etc.`.
    * **`Produto.java`**: Modela a entidade Produto, com atributos `ID, GTIN-13, nome, descrição, e um status de ativo/inativo`.
    * **`ListaProduto.java`**: Entidade de associação para o relacionamento N:N entre `Lista` e `Produto`, com atributos `ID, ID da Lista, ID do Produto, quantidade e observações`.

* **`Arquivo` (Model/DAO):** Camada responsável pelo acesso direto e gerenciamento dos arquivos de dados, além da orquestração de seus índices. A pasta `Dados` agora é gerada dentro do diretório do TP ativo (`TP_03 - Índice Invertido/Dados`).
    * **`Arquivo.java`**: Classe genérica de baixo nível que implementa CRUD para qualquer entidade, gerenciando **lápide**, **tamanho dos registros** e **índice direto primário** (ID → Endereço).
    * **`ArquivoUsuario.java`**: Especialização de `Arquivo` para gerenciar o **usuarios.db**. Inclui um **índice indireto de e-mails** (via `HashExtensivel`) e a **regra de negócio que impede exclusão de usuários com listas vinculadas**.
    * **`ArquivoLista.java`**: Especialização de `Arquivo` para gerenciar o **listas.db**. Implementa **índices secundários**, uma **Árvore B+** para o relacionamento 1:N (idUsuario → idLista) e uma **Tabela Hash** para busca por código compartilhável (código → idLista). Gerencia a **exclusão em cascata** de associações `ListaProduto`.
    * **`ArquivoProduto.java`**: Especialização de `Arquivo` para gerenciar o **produtos.db**. Implementa um **índice indireto de GTIN-13** (via `HashExtensivel`) e as operações de **inativação/reativação** de produtos e gerencia o Índice Invertido **(`ArvoreBMais<ParTermoProduto>`)** para a busca por relevância (TF-IDF).
    * **`ArquivoListaProduto.java`**: Especialização de `Arquivo` para gerenciar o **lista_produto.db**. Implementa **duas Árvores B+** para o relacionamento N:N: uma para buscar associações por `idLista` (`idLista` → `idListaProduto`) e outra por `idProduto` (`idProduto` → `idListaProduto`).
    * **`ArquivoNanoID.java`**: Classe utilitária para geração de códigos compartilháveis.

* **`Views` (View):** Camada de **Visão**, responsável pela interação com o usuário via console.
    * **`VisaoUsuario.java`**: Exibe o menu **"Meus Dados"**, solicita dados para alteração e confirmações.
    * **`VisaoLista.java`**: Exibe menus de listagem e detalhes das listas, além de ler dados para criação e alteração. Inclui menus para gerenciamento e adição de produtos na lista.
    * **`VisaoBuscaLista.java`**: Exibe a interface de busca de listas por código, mostrando agora o autor e os produtos da lista.
    * **`VisaoProduto.java`**: Exibe menus para o gerenciamento global de produtos, incluindo a nova interface de busca unificada e detalhes completos de um produto.

* **`Controles` (Controller):** Camada de **Controle**, que orquestra o fluxo da aplicação. Os *Controllers* respondem às ações do usuário (capturadas pelas *Views*) e manipulam os dados (via *Model*).
    * **`ControleUsuario.java`**: Gerencia a lógica do menu **"Meus Dados"**, incluindo alteração e exclusão da conta, e a **confirmação por resposta secreta**.
    * **`ControleLista.java`**: Gerencia a lógica de **CRUD** (criar, ler, alterar, deletar) das listas do usuário logado e orquestra o **gerenciamento de produtos na lista**.
    * **`ControleBuscaLista.java`**: Gerencia a lógica da busca global de listas por código, buscando e exibindo informações detalhadas do autor e dos produtos.
    * **`ControleProduto.java`**: Gerencia a lógica de **CRUD** de produtos (cadastro, busca, listagem, inativação/reativação), incluindo **listagem paginada** e **detalhes de produto** (com suas listas associadas) e orquestra a **busca unificada**, decidindo se a consulta do usuário é um GTIN-13 ou palavras-chave para a busca TF-IDF..

* **`Menus`:** Contém classes de navegação de alto nível e utilidades de interface.
    * **`MenuUsuarios.java`**: Atua como **controller principal**, gerenciando o fluxo de login, a criação de conta e o menu principal pós-login, delegando ações para os *Controllers*.
    * **`ConsoleUtils.java`**: Classe utilitária com métodos para limpar tela, pausar console e copiar texto para área de transferência.

* **`Utils`:** Classes utilitárias de propósito geral.
    * `ProcessadorTexto.java`: Classe responsável por normalizar, remover acentos, remover *stop words* e calcular o TF (Term Frequency) dos nomes de produtos antes da indexação.

* **`Estruturas`:** Contém os componentes genéricos e reutilizáveis que formam a base do sistema de persistência e indexação.
    * **`HashExtensivel.java`**: Implementação de *Hash Extensível*.
    * **`ArvoreBMais.java`**: Implementação de **Árvore B+**.

* **`Pares`:** Contém classes auxiliares que representam os registros de índices.
    * **`ParCodigoLista.java`**: Relaciona código de lista com ID de lista.
    * **`ParEmailID.java`**: Relaciona e-mail com ID de usuário.
    * **`ParIDEndereco.java`**: Relaciona ID com endereço em arquivo.
    * **`ParUsuarioLista.java`**: Relaciona ID de usuário com ID de lista (para o índice 1:N).
    * **`ParGtinId.java`**: Relaciona GTIN-13 com ID de produto.
    * **`ParListaProduto.java`**: Relaciona ID de lista com ID de associação ListaProduto (para o índice N:N).
    * **`ParProdutoLista.java`**: Relaciona ID de produto com ID de associação ListaProduto (para o índice N:N).
    * **`ParTermoProduto.java`**: Par que representa a entrada do índice invertido, relacionando um `termo` a um `idProduto` e seu `TF` (Term Frequency).

* **`Registros`:** Define contratos (interfaces) para que entidades e registros possam ser gerenciados pelo sistema de arquivos e índices.
    * **`Registro.java`**: Interface base para entidades persistentes.
    * **`RegistroHashExtensivel.java`**: Interface para registros compatíveis com Hash Extensível.
    * **`RegistroArvoreBMais.java`**: Interface para registros compatíveis com Árvore B+.

* **`Main.java`**: Classe de entrada da aplicação, responsável por instanciar o menu inicial e iniciar o programa.

---

## 🖥️ Telas do Sistema

1.  **Menu Inicial** ![Menu Inicial com opções de login e cadastro](img/1%20-%20Tela_MenuInicial.png)
2.  **Cadastro de Novo Usuário** ![Tela de cadastro de novo usuário](img/2%20-%20Tela_MenuInicial_Cadastro.png)
3.  **Tela de Login** ![Tela de login do usuário](img/3%20-%20Tela_MenuInicial_Login.png)
4.  **Menu Principal (Pós-Login)** ![Menu principal exibido após o login do usuário](img/4%20-%20Tela_MenuPrincipal_Logado.png)
5.  **Tela "Meus Dados" do Usuário** ![Tela com as informações de cadastro do usuário e opções de gerenciamento](img/5%20-%20Tela_UsuarioMeusDados.png)
6.  **Tela "Minhas Listas"** ![Tela exibindo a listagem de todas as listas do usuário em ordem alfabética](img/6%20-%20Tela_UsuarioMinhasListas.png)
7.  **Criação de Nova Lista** ![Tela de cadastro de uma nova lista de presentes](img/7%20-%20Tela_UsuarioMinhasListas_NovaLista.png)
8.  **Detalhes de uma Lista Selecionada** ![Tela exibindo os detalhes de uma lista específica, com suas opções de gerenciamento](img/8%20-%20Tela_UsuarioMinhasListas_ListaSelecionada.png)
9.  **Gerenciamento de Produtos na Lista Selecionada** ![Tela de gerenciamento de produtos dentro de uma lista, mostrando itens e opções](img/9%20-%20Tela_UsuarioMinhasListas_ListaSelecionada_Produtos.png)
10. **Copiando Código de Compartilhamento de uma Lista** ![Tela mostrando a confirmação de que o código da lista foi copiado](img/10%20-%20Tela_UsuarioMinhasListas_CodigoLista.png)
11. **Menu Principal de Produtos** ![Menu com as opções de gerenciamento de produtos](img/11%20-%20ProcurarProdutos.png)
12. **Busca de Produto por GTIN** ![Tela de busca de um produto específico pelo seu código GTIN-13](img/12%20-%20ProcurarProdutos_PorGTIN.png)
13. **Detalhes de um Produto Selecionada** ![Tela de busca de um produto específico pelo seu código GTIN-13](img/13%20-%20ProcurarProdutos_PorGTIN(Encontrado).png)
14. **Busca de Produto por Palavra Chave** ![Tela de busca de um produto específico pelo seu código GTIN-13](img/14%20-%20ProcurarProdutos_PorPalavraChave.png)
15. **Produtos Encontrados pela Busca de Palavra Chave** ![Tela de busca de um produto específico pelo seu código GTIN-13](img/15%20-%20ProcurarProdutos_PorPalavraChave(Encontrado).png)
16. **Listagem Paginada de Produtos** ![Listagem de produtos com paginação, mostrando ativos e inativos ordenados](img/16%20-%20ProcurarProdutos_Listando.png)
17. **Busca Global de Lista por Código de Lista** ![Tela de busca de uma lista por seu código de compartilhamento](img/17%20-%20ProcurarListas_porCodigo.png)

---

## Checklist de Avaliação (TP1, TP2 e TP3)

### **TP1:**
> **Há um CRUD de usuários (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis como índices) que funciona corretamente?** ✅
>
> **Sim.** A classe `ArquivoUsuario.java` gerencia o CRUD de usuários, utilizando um índice de Hash Extensível para o e-mail, permitindo buscas e login eficientes.

> **Há um CRUD de listas (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices) que funciona corretamente?** ✅
>
> **Sim.** A classe `ArquivoLista.java` gerencia o CRUD de listas. Ela utiliza uma Árvore B+ para o relacionamento 1:N com o usuário e uma Tabela Hash Extensível para a busca por código compartilhável.

> **As listas de presentes estão vinculadas aos usuários usando o idUsuario como chave estrangeira?** ✅
>
> **Sim.** A classe `Lista.java` possui o atributo `idUsuario`, que funciona como chave estrangeira, garantindo que cada lista pertença a um único usuário.

> **Há uma árvore B+ que registre o relacionamento 1:N entre usuários e listas?** ✅
>
> **Sim.** A classe `ArquivoLista.java` utiliza uma `ArvoreBMais<ParUsuarioLista>` que armazena pares `(idUsuario, idLista)`, implementando eficientemente a busca de todas as listas de um determinado usuário.

> **Há uma visualização das listas de outras pessoas por meio de um código NanoID?** ✅
>
> **Sim.** A opção "(4) Buscar lista" do menu principal permite que qualquer pessoa insira um código de 10 caracteres e visualize os detalhes da lista, incluindo seu autor e produtos.

### TP2

> **Há um CRUD de produtos (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?** ✅
>
> **Sim.** A classe `ArquivoProduto.java` gerencia o CRUD de produtos, com índice Hash para o GTIN-13. A "exclusão" é tratada como inativação para manter a integridade referencial.

> **Há um CRUD da entidade de associação ListaProduto (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices diretos e indiretos conforme necessidade) que funciona corretamente?** ✅
>
> **Sim.** A classe `ArquivoListaProduto.java` gerencia o CRUD da entidade de associação, utilizando duas Árvores B+ para indexar o relacionamento N:N por `idLista` e por `idProduto`, permitindo a recuperação eficiente das associações.

> **A visão de produtos está corretamente implementada e permite consultas as listas em que o produto aparece (apenas quantidade no caso de lista de outras pessoas)?** ✅
>
> **Sim.** A `VisaoProduto.java` e `ControleProduto.java` implementam a listagem paginada e, ao detalhar um produto, exibe em quais listas do usuário logado ele se encontra e a contagem de listas de outros usuários.

> **A visão de listas funciona corretamente e permite a gestão dos produtos na lista?** ✅
>
> **Sim.** A `VisaoLista.java` e `ControleLista.java` permitem gerenciar produtos, incluindo adição (com busca paginada), alteração de quantidade/observações e remoção. A exibição dos produtos é ordenada alfabeticamente.

> **A integridade do relacionamento entre listas e produtos está mantida em todas as operações?** ✅
>
> **Sim.** A integridade é garantida através de exclusão em cascata (Lista → ListaProduto), validações de dados (GTIN-13) e a lógica de inativação de produtos em vez de exclusão física.

### TP3

> **O índice invertido com os termos dos nomes dos produtos foi criado usando a classe ListaInvertida?** ✅
>
> **Sim.** Como a classe `ListaInvertida` não foi fornecida, implementamos nossa própria estrutura de índice invertido utilizando a `ArvoreBMais` (disponibilizada na disciplina) e um novo par `Pares/ParTermoProduto.java`. Esta abordagem é ideal para o relacionamento 1:N (1 Termo → N Produtos) e se integra perfeitamente à arquitetura do projeto.

> **É possível buscar produtos por palavras no menu de manutenção de produtos?** ✅
>
> **Sim.** A busca foi unificada no menu "Produtos". O sistema, através do `ControleProduto`, identifica se a entrada é um GTIN-13 ou palavras-chave. No segundo caso, ele chama o método `searchByTerms` de `ArquivoProduto`, que usa o índice invertido e o ranking TF-IDF para ordenar os resultados por relevância.

> **É possível buscar produtos por palavras na hora de acrescentá-los às listas dos usuários?** ✅
>
> **Sim.** O menu "Acrescentar Produto" (dentro de "Minhas Listas") também foi unificado. Ao buscar por palavras, o `ControleLista` chama o mesmo `searchByTerms`, mas filtra os resultados para exibir **apenas produtos ativos**, mantendo a regra de negócio que impede a adição de itens inativos.

### Comuns a ambos

> **O trabalho compila corretamente?** ✅
> 
> **Sim.** O projeto foi estruturado com pacotes e compila sem erros a partir do diretório raiz.

> **O trabalho está completo e funcionando sem erros de execução?** ✅
> 
> **Sim.** Todas as funcionalidades descritas no escopo do TP1 e TP2 foram implementadas e testadas, funcionando conforme o esperado e com tratamento de exceções para uma execução estável.

> **O trabalho é original e não a cópia de um trabalho de outro grupo?** ✅
> 
> **Sim.** O trabalho foi desenvolvido pelos participantes listados, com base nas estruturas de dados fornecidas e nos requisitos do enunciado.

---

## 🎥 Vídeo de Demonstração

* **TP1:** [https://youtu.be/ZYMZ9jcaeK0](https://youtu.be/ZYMZ9jcaeK0)
* **TP2:** [https://youtu.be/GzxbBbwcHy0](https://youtu.be/GzxbBbwcHy0)
* **TP3:** [https://youtu.be/TK6eKy3TGHQ](https://youtu.be/TK6eKy3TGHQ)