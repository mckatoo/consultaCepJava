# ConsultaCepJava

![GitHub repo size](https://img.shields.io/github/repo-size/mckatoo/consultaCepJava) ![GitHub All Releases](https://img.shields.io/github/downloads/mckatoo/consultaCepJava/total) ![GitHub issues](https://img.shields.io/github/issues/mckatoo/consultaCepJava) ![GitHub](https://img.shields.io/github/license/mckatoo/consultaCepJava) ![GitHub followers](https://img.shields.io/github/followers/mckatoo) ![GitHub forks](https://img.shields.io/github/forks/mckatoo/consultacepjava) ![GitHub stars](https://img.shields.io/github/stars/mckatoo/consultacepjava) ![GitHub watchers](https://img.shields.io/github/watchers/mckatoo/consultacepjava) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/mckatoo/consultacepjava) ![GitHub contributors](https://img.shields.io/github/contributors/mckatoo/consultacepjava) ![GitHub last commit](https://img.shields.io/github/last-commit/mckatoo/consultacepjava) ![GitHub top language](https://img.shields.io/github/languages/top/mckatoo/consultacepjava) 

## Como usar
[Faça download da biblioteca aqui.](https://github.com/mckatoo/consultaCepJava/blob/master/store/ConsultaCEP.jar)

No Netbeans, clique com o botão direito do mouse sobre `Bibliotecas`/`Importar`/`Arquivo Jar`, e procure o arquivo baixado no link acima.

## Exemplo de uso:
```java
CEPBean cep = new CEPBean();                    \\ CEPBean pertence à biblioteca
cep = Cep.consultaCEP(txtCEP.getText());        \\ Método consultaCEP usa como parametro uma String no formato 00000000
txtEndereco.setText(cep.getEndereco() + ", ");  \\ Daqui pra baixo está preenchendo JTextViews como os atributos do objeto.
txtBairro.setText(cep.getBairro());             \\ Mas pode ser usado de outras maneiras. Use sua imaginação. ;)
txtCidade.setText(cep.getCidade());
txtUF.setText(cep.getUF());
```
## Descrição do projeto
Pesquisa cep nas seguintes api's e nesta ordem:
 1. ViaCEP
 2. CepAberto
 3. Correios
 
## Futuras implementações
- Fazer consulta das api's de modo conconrrente.
- Padronizar métodos e retornos.
