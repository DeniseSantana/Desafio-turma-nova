# TEMPLATE DE AUTOMAÇÃO HÍBRIDO

## Descrição
Template de automação multi-thread mobile com integração ao UFT Mobile, web com integração ao SauceLabs e RestAssured.

## Estrutura
O projeto está estruturado em dois pacotes principais, core e test.
No pacote core existem as funcionalidades que dão suporte aos testes: utilitários de driver, planilha, entre outros.
No pacote test é onde ficam os scripts construídos e sua estrutura.

Recomendamos utilizar as seguintes camadas:
	page -> logic -> steps -> runner

Na camada page é onde ficam os page objects, sem qualquer interação com os elementos que o compõem, apenas os mapeamentos.
Na camada logic é onde criamos métodos para interação com os objetos das pages.
Na camada steps consolidamos varias logics e estruturamos os steps do BDD, nessa camada também consumimos a massa de dados, que é implmentada de acordo com a regra de negocio de cada projeto.
Na camada runner é onde chamamos os cenários para ser executados.

Utilizamos os seguintes arquivos de properties:
application.properties: Possui todas as propriedades com opções para execução da automação;  
devices.json: Contém todas as informações referente ao dispositivo local e/ou no UFT Mobile;  
report.properties: Contém configurações de evidencia;  
rest.properties: Contém as chaves para configurar a conexão API rest;  
saucelabs.properties: Contém todas as configurações dos navegadores utilizados no SauceLabs;  
uftmobile.properties: Contém configurações especificas para estabelecer conexão com o servido do UFT Mobile;  
zgen.properties: Contém configurações especificas para realizar o 'upload' das evidencias no MCN/Zgen.  

## Ferramentas

Estão implementadas as seguintes funcionalidades:
Drivers web locais - 'CHROME', 'FIREFOX', 'IE', 'EDGE' and 'OPERA';  
Driver SauceLabs - 'CHROME', 'FIREFOX', 'SAFARI' e 'EDGE';  
Drivers mobile locais - ANDROID e IOS;  
Conexão com UFT Mobile;  
Utilitário com RestAssured;  

Evidências em PDF e DOCX;  

Consumo de massa de testes de arquivos excel.  

## Execução via Maven
Para executar via linha de comando Maven, use: 'mvn -Dtest=<nome_da_classe> test'
Exemplo: mvn -Dtest=GoogleRunner test

## Exemplos
Na camada test já existem 3 exemplos implementados de testes Web, Mobile e Rest, com consumo de massa em excel. São eles: GoogleRunner (web), MobileRunner (mobile) e RestRunner (rest).