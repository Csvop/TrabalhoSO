

<h3 align="center">
  <img src="https://img.shields.io/badge/platform-windows%20%7C%20linux%20%7C%20macos-blue" />
  <img src="https://img.shields.io/badge/java-%3E%3D13.0.0-blue" />
  <img src="https://img.shields.io/badge/gradle-6.1.1-blue" />
  <a href="https://gitpod.io/#https://github.com/Csvop/TrabalhoSO/">
    <img src="https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod" />
  </a>
  <p></p>
  <p align="center">PUCRS - Escola Politécnica - 2021/1</p>
  <p align="center">Disciplina: Sistemas Operacionais</p>
  <p align="center">Prof. Fernando Luís Dotti</p>
</h3>

# Máquina Virtual

Nossa máquina virtual tem CPU, Memória, Gerente de Memória e Gerente de Processo.

**Enunciado do trabalho**: Em grupos de até 3 alunos. Construir uma máquina virtual conforme definido no enunciado do trabalho que está no Moodle com o enunciado da primeira parte do trabalho.

## Nome dos integrantes

- Bruno Garcia
- Leonardo Machado
- Robson Bittencourt


## 1. CPU 

 A máquina virtual deve poder executar programas descritos com o conjunto de instruções da CPU (```OPCODES```). 
 Como parte do trabalho voce deve construir alguns programas. 
 Em cada teste da máquina virtual, um destes programas é carregado a partir da posição ```0``` da memória e a CPU então é liberada para executar fazendo seu ciclo.


## 🔦ㅤPré Requisitos

Antes de começar, você vai precisar ter instalado o [JDK](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html) localmente no seu computador para compilar o código. 
Além disto é bom ter um editor para trabalhar com o código como [VSCode](https://code.visualstudio.com/).
Usando a IDE de browser [GitPod](https://gitpod.io/) não é preciso instalar nada localmente no seu computador.

## 🏃ㅤGetting Started (Seção implementação)

Para executar o Sistema execute o método `main()` do arquivo `App.java`.

```bash
# Acesse a pasta do projeto no terminal
$ cd TrabalhoSO/

# Compila o código
$ javac App.java

# Execute a aplicação
$ java App
```

###### Se acontecer erro na hora do clone/checkout, o comando `git config core.protectNTFS false` pode consertar.

Para escolher qual programa executar, remova o comentário referente ao programa desejado.
É possível executar vários programas em paralelo.

```java
public class App {
    public static void main(String[] args) {
		VM vm = new VM();

		//Cria processos na memória e coloca na fila de prontos
		vm.load(Program.PC);
		// vm.load(Program.PB);
		// vm.load(Program.PA);
		// vm.load(Program.TRAP_IN);
		// vm.load(Program.TRAP_OUT);

        ...
    }
}
```

## 🚀ㅤFeatures

- [x] CPU
- [x] Memória
- [x] Interrupções
- [x] Chamadas de Sistema
- [x] Gerência de Memória
- [x] Gerência de Processos
- [x] Escalonamento
- [ ] Concorrência
- [ ] Semáforos

## Relatório

### Seção Programas

Foram feitos todos os programas requeridos: PA, PB e PC. Também tem o TRAP_IN e TRAP_OUT.

### Seção de Saídas

#### PA
<div align="center"><img src=https://cdn.discordapp.com/attachments/872968154723270657/880547929516015716/unknown.png /></div>

#### PB
<div align="center"><img src=https://cdn.discordapp.com/attachments/872968154723270657/880548097376260176/unknown.png /></div>

#### PC
não rodou :(


### Seção Considerações
A utilização do gradle pelo comando `gradle run` pode causar alguns problemas quando são executados programas que precisam ler valores diretamente do terminal (`System.in`).

## 🛠ㅤTecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Git](https://git-scm.com/)
- [Java](https://www.java.com/)
- [Gitpod](https://gitpod.io/)
- [Gradle](https://gradle.org/install/)
