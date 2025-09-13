# PresenteFácil 1.0

## Alunos participantes

* [Felipe Rivetti Mizher](https://github.com/FelipeMizher)
* [Matheus Felipe Cavalcanti Xavier](https://github.com/mioj0kt)

## Professor responsável

* Marcos Andre Silveira Kutova

## 📝 Descrição do Sistema

Nosso trabalho implementa um sistema de gerenciamento de listas, permitindo aos usuários criar, visualizar, atualizar e excluir listas de diferentes tipos, como listas de aniversários, listas de matérias ou listas de tarefas. O sistema oferece funcionalidades completas de CRUD (Create, Read, Update, Delete), garantindo organização e facilidade na manipulação dos dados dos usuários. Além disso, conta com autenticação de usuários e persistência dos dados em arquivos estruturados

---

### Principais funcionalidades
- **Gerenciamento de Usuários:**  
  Cadastro de novos usuários com nome, e-mail, senha e perguntas de segurança.  
  A senha é armazenada de forma segura utilizando **hash SHA-256**.

- **Login de Usuário:**  
  Autenticação de usuários através de e-mail e senha para acesso às funcionalidades personalizadas.

- **Gerenciamento de Listas:**  
  Criação, visualização, alteração e exclusão de listas de presentes.  
  Cada lista possui nome, descrição, data de criação e uma data limite opcional.

- **Visualização Ordenada:**  
  As listas de um usuário são sempre exibidas em **ordem alfabética**.

- **Código Compartilhável:**  
  Cada lista criada gera automaticamente um código único de **10 caracteres (NanoID)**.

- **Busca Pública de Listas:**  
  Qualquer pessoa (logada ou não) pode buscar uma lista pelo código compartilhável.
  
---

## Arquitetura e Classes Criadas

O sistema segue o padrão **MVC (Model-View-Controller)**.  
A persistência dos dados é realizada em arquivos binários com **CRUD genérico**, utilizando **Hash Extensível** e **Árvore B+** para indexação.

## 📂 Pacote **Entidades**
Representa o núcleo do Modelo, contendo as classes que definem os objetos de negócio do sistema.

- **Usuario.java** → Modela a entidade Usuário, com atributos como:  
  `ID, nome, e-mail, hash da senha, pergunta secreta, resposta secreta, etc.`  

- **Lista.java** → Modela a entidade Lista, com atributos como:  
  `ID, ID do usuário, nome, descrição, código compartilhável, etc.`  


## 📂 Pacote **Arquivo**
Camada responsável pelo acesso direto e gerenciamento dos arquivos de dados, além da orquestração de seus índices.

- **Arquivo.java** → Classe genérica de baixo nível que implementa CRUD para qualquer entidade, gerenciando:
  - Lápide  
  - Tamanho dos registros  
  - Índice direto primário (ID → Endereço)  

- **ArquivoUsuario.java** → Especialização de `Arquivo` para gerenciar o **usuarios.db**.  
  Inclui:
  - Índice indireto de e-mails (via `HashExtensivel`)  
  - Regra de negócio que impede exclusão de usuários com listas vinculadas  

- **ArquivoLista.java** → Especialização de `Arquivo` para gerenciar o **listas.db**.  
  Implementa:
  - Índices secundários  
  - **Árvore B+** para o relacionamento 1:N  
  - **Tabela Hash** para busca por código compartilhável  



## 📂 Pacote **Views**
Camada de **Visão**, responsável pela interação com o usuário via console.

- **VisaoUsuario.java** → Exibe o menu **"Meus Dados"**, solicita dados para alteração e confirmações.  
- **VisaoLista.java** → Exibe menus de listagem e detalhes das listas, além de ler dados para criação e alteração.  
- **VisaoBuscaLista.java** → Exibe a interface de busca de listas por código.  



## 📂 Pacote **Controles**
Camada de **Controle**, que orquestra o fluxo da aplicação.  
Os *Controllers* respondem às ações do usuário (capturadas pelas *Views*) e manipulam os dados (via *Model*).

- **ControleUsuario.java** → Gerencia a lógica do menu **"Meus Dados"**, incluindo alteração e exclusão da conta.  
- **ControleLista.java** → Gerencia a lógica de **CRUD** (criar, ler, alterar, deletar) das listas do usuário logado.  
- **ControleBuscaLista.java** → Gerencia a lógica da busca global de listas por código.  



## 📂 Pacote **Menus**
Contém classes de navegação de alto nível e utilidades de interface.

- **MenuUsuarios.java** → Atua como **controller principal**, gerenciando:
  - Fluxo de login  
  - Criação de conta  
  - Menu principal pós-login (delegando ações para os *Controllers*)  

- **ConsoleUtils.java** → Classe utilitária com métodos para:
  - Limpar tela  
  - Pausar console  
  - Copiar texto para área de transferência  



## 📂 Pacote **Estruturas**
Contém os componentes genéricos e reutilizáveis que formam a base do sistema de persistência e indexação.

- **HashExtensivel.java** → Implementação de *Hash Extensível*.  
- **ArvoreBMais.java** → Implementação de **Árvore B+**.  



## 📂 Pacote **Pares**
Contém classes auxiliares que representam os registros de índices.

- **ParCodigoLista.java** → Relaciona código de lista com lista.  
- **ParEmailID.java** → Relaciona e-mail com ID de usuário.  
- **ParIDEndereco.java** → Relaciona ID com endereço em arquivo.  
- **ParUsuarioLista.java** → Relaciona usuário com listas.  



## 📂 Pacote **Registros**
Define contratos para que entidades e registros possam ser gerenciados pelo sistema de arquivos e índices.

- **Registro.java** → Interface base para registros.  
- **RegistroHashExtensivel.java** → Registro para *Hash Extensível*.  
- **RegistroArvoreBMais.java** → Registro para *Árvore B+*.  



## 📄 Classe Principal
- **Main.java** → Classe de entrada da aplicação.  
  Responsável por instanciar o menu inicial e iniciar o programa.  

---

## Telas do Sistema

1. **Menu Inicial**  
   ![Tela de Login / Novo Usuário](screenshot1.png)

2. **Menu Principal do Usuário Logado**  
   ![Menu principal](screenshot2.png)

3. **Tela "Minhas Listas"**  
   ![Listas ordenadas](screenshot3.png)

4. **Tela de Detalhes da Lista**  
   ![Detalhes + código compartilhável](screenshot4.png)

5. **Tela de Busca de Lista**  
   ![Busca pelo código compartilhável](screenshot5.png)

---

## 🔍 Operações Especiais Implementadas

- **Persistência em Arquivo com Lápide:**  
  - Dados salvos em `.db` com exclusão lógica, permitindo reaproveitamento de espaço.  

- **Indexação para Acesso Rápido:**  
  - **Login por E-mail:** Hash Extensível mapeando e-mail → idUsuario.  
  - **Relação Usuário → Listas:** Árvore B+ para mapear idUsuario → idLista.  
  - **Busca por Código Compartilhável:** Hash Extensível para código → idLista.

- **Regra de Integridade Referencial:**
  - Para garantir a consistência dos dados, foi implementada uma regra de negócio que impede a exclusão de um usuário caso ele possua uma ou mais listas de presentes ativas. O sistema primeiramente verifica a existência de listas vinculadas antes de permitir a operação de exclusão do usuário, prevenindo a existência de listas "órfãs" no banco de dados.
 
- **Cópia Automática do Código Compartilhável:**
  - Para facilitar o compartilhamento das listas, ao visualizar os detalhes de uma lista, o sistema exibe o código único e disponibiliza uma opção para copiá-lo automaticamente para a área de transferência do usuário, melhorando a usabilidade.
 
- **Visualização Ordenada das Listas:**
  - Para melhorar a experiência do usuário, o sistema não exibe as listas na ordem em que foram criadas. Em vez disso, a classe `ControleLista.java` sempre realiza uma ordenação alfabética pelo nome antes de exibir o menu "Minhas Listas". Isso garante que o usuário possa encontrar suas listas de forma rápida e intuitiva, especialmente quando possuir um grande número delas.
 

---

## Checklist de Avaliação

Há um CRUD de usuários (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis como índices) que funciona corretamente? ✅

**Sim.** A classe `ArquivoUsuario.java` gerencia o CRUD de usuários, utilizando um índice de Hash Extensível para o e-mail, permitindo buscas e login eficientes.  

---

Há um CRUD de listas (que estende a classe Arquivo, acrescentando Tabelas Hash Extensíveis e Árvores B+ como índices) que funciona corretamente? ✅

**Sim.** A classe `ArquivoLista.java` gerencia o CRUD de listas. Ela utiliza uma Árvore B+ para o relacionamento 1:N com o usuário e uma Tabela Hash Extensível para a busca por código compartilhável.  

---

As listas de presentes estão vinculadas aos usuários usando o idUsuario como chave estrangeira? ✅

**Sim.** A classe `Lista.java` possui o atributo `idUsuario`, que funciona como chave estrangeira, garantindo que cada lista pertença a um único usuário.  

---

Há uma árvore B+ que registre o relacionamento 1:N entre usuários e listas? ✅

**Sim.** A classe `ArquivoLista.java` utiliza uma `ArvoreBMais<ParUsuarioLista>` que armazena pares `(idUsuario, idLista)`, implementando eficientemente a busca de todas as listas de um determinado usuário.  

---

Há uma visualização das listas de outras pessoas por meio de um código NanoID? ✅

**Sim.** A opção **"(4) Buscar lista"** do menu principal permite que qualquer pessoa insira um código de 10 caracteres e visualize os detalhes da lista correspondente, se ela existir.  

---

O trabalho compila corretamente? ✅

**Sim.** O projeto foi estruturado com pacotes (`app` e `aed3`) e compila sem erros a partir do diretório raiz.  

---

O trabalho está completo e funcionando sem erros de execução? ✅

**Sim.** Todas as funcionalidades descritas no escopo do TP1 foram implementadas e testadas, funcionando conforme o esperado e com tratamento de exceções para uma execução estável.  

---

O trabalho é original e não a cópia de um trabalho de outro grupo? ✅

**Sim.** O trabalho foi desenvolvido pelos participantes listados, com base nas estruturas de dados fornecidas e nos requisitos do enunciado.  


---

## Vídeo de Demonstração


---
