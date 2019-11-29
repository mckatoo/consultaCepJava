# ConsultaCepJava

![GitHub repo size](https://img.shields.io/github/repo-size/mckatoo/consultaCepJava)
![GitHub All Releases](https://img.shields.io/github/downloads/mckatoo/consultaCepJava/total)
![GitHub issues](https://img.shields.io/github/issues/mckatoo/consultaCepJava)
![GitHub](https://img.shields.io/github/license/mckatoo/consultaCepJava)
![GitHub followers](https://img.shields.io/github/followers/mckatoo)
![GitHub forks](https://img.shields.io/github/forks/mckatoo/consultacepjava)
![GitHub stars](https://img.shields.io/github/stars/mckatoo/consultacepjava)
![GitHub watchers](https://img.shields.io/github/watchers/mckatoo/consultacepjava)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/mckatoo/consultacepjava)
![GitHub contributors](https://img.shields.io/github/contributors/mckatoo/consultacepjava)
![GitHub last commit](https://img.shields.io/github/last-commit/mckatoo/consultacepjava)
![GitHub top language](https://img.shields.io/github/languages/top/mckatoo/consultacepjava)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/mckatoo/consultacepjava)

## Como usar
[Faça download da biblioteca aqui.](https://github.com/mckatoo/consultaCepJava/blob/master/store/ConsultaCEP.jar)

No Netbeans, clique com o botão direito do mouse sobre `Bibliotecas`/`Importar`/`Arquivo Jar`, e procure o arquivo baixado no link acima.

## Exemplo de uso:

### Modo Síncrono
```java
CEPBean cepBean = Cep.consultaCEP("13973481");
System.out.println("API DE CEP MAIS RÁPIDA NA CONSULTA SÍNCRONA = " + cepBean.getApi());
```

### Modo Assíncrono
```java
CEPBean cepBean = Cep.consultaCEPasync("13973481").get(10, TimeUnit.SECONDS);
System.out.println("API DE CEP MAIS RÁPIDA NA CONSULTA ASSÍNCRONA = " + cepBean.getApi());
```

## Descrição do projeto
Pesquisa cep nas seguintes api's e de modo assíncrono:
 1. ViaCEP
 2. CepAberto
 3. Widenet
 4. WebManiaBr
 5. PostMon
 
## Futuras implementações
- Padronizar métodos e retornos.
