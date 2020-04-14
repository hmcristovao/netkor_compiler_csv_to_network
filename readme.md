# NetKOR Mapping Compiler
---
## Compilador para mapear arquivos CSV para Network 

Arquivo de entrada: CSV - contém a base de dados a ser mapeada

Arquivo de mapeamnento: MAP - contém a programação a ser usada no mapeamento do arquivo CVS para o formato Network

Arquivo de network: NET (default) - contém a saída a ser gerada pelo mapeamento realizado sobre o arquivo CSV

---

### Melhorias
#### A) Novas funcionalidades - Nível de prioridade MÁXIMO 
1. ~~Permitir a colocação de variáveis de cabeçalho na seção 3. Assim os nós são criados usando-se os valores dessas variáveis. Atualmente são permitidas apenas constantes.~~ 
2. ~~Em Section 2, permitir nome de identificador sozinho (sem dois pontos seguido de outro identificador). Nesse caso ele serve para identificar, com o mesmo nome, o cabeçalho de coluna do CSV e a variável interna usada no MAP.~~
3. ~~Cabeçalho das colunas do CSV com possibilidade de acentuação.~~
4. ~~Possibilitar números decimais com ponto ou com vírgula.~~
5. Implementar "Column separator:" em Section 1: permite configurar o caractere usado para separar os campos do CSV (o default seria a vírgula). 
6. Tratar uma expressão do tipo data de calendário. Assim aceitar operações com os relacionais e intervalos.
7. Tratar uma expressão string alfabeticamente aceitando expressões alfabéticas para definir intervalos. 
8. Implementar "Text delimiter:" em Section 1: permite configurar o caractere usado para envolver valores das colunas evitando  conflitos. Por exemplo, se a vírgula é usada para separar campos e ao mesmo tempo como conteúdo de um campo. 
9. Aceitar o formato GDF - https://gephi.org/users/supported-graph-formats/gdf-format/, compatíveis com as novas definições do documento MAP

#### B) Novas funcionalidades - Nível de prioridade MÉDIO
1. Permitir mesclagem de colunas. Exemplo do caso do documento Emendas.csv nas colunas nome Nome Subfunção + “ (“ + Nome Função + “)” 
2. Permitir o descarte de linhas que atendam um determinado critério. Exemplo do caso do documento Emendas.csv onde foram descartadas 1500 linhas cujo critério foi Código IBGE Estado = -2
3. Permitir a criação de colunas “virtuais” a partir de expressões envolvendo as colunas existentes. Por exemplo: coluna “nome do mês” é a extração do mês de uma data; coluna “taxa de feridos graves e mortos” é a razão da soma da qtde de feridos graves e mortos de um acidente pela qtde de ocupantes do veículo. 
4. Aceitar mistura de tipos. Exemplo, a coluna km contém a quilometragem do acidente, mas pode ter a string “NA” como não conhecido. Permitir que isso seja usado em expressões, tal como:
“km 0-10” : km <= 10
“Km NA” : km = “NA”
5. Cabeçalhos com possibilidade de espaços em branco. Nesse caso, a descrição da coluna ficaria entre o caractere definido em Text delimiter. 

#### C) Análise Semântica
1. ~~Definição de variáveis com nomes iguais~~
2. ~~Definição de uma variável para duas ou mais colunas~~
3. Variável não utilizada na definição dos vértices de incidência (Klaus, isso deve ser um Warning!)
4. Verificar se todas as variáveis usadas nas expressões da #Section 3 foram devidamente definidas em #Section 2.
5. Vértices de incidência com o mesmo nome (o que é isso Klaus?)
6. O Caractere definido como delimitador aparece no conteúdo de campo do CVS, sendo ainda que esse não está envolto pelo caractere definido em "Text delimiter" (Section 1).
7. Quantidade de valores de uma linha é incompatível com a quantidade de cabeçalhos do CSV.
  
---
### Universidade Federal do Espírito Santo
#### Grupo de pesquisa NetKOR (Networked Knowledge Organization Retrieval)  
#### Desenvolvimento:
##### Coordenador
* Henrique Monteiro Cristovão - hmcristovao@gmail.com
##### Colaboradores
* Klaus Kly Cuzzuol Wolff - darkkcw@gmail.com
* Luis Henrique Gundes Valim - henriquegundes@outlook.com
