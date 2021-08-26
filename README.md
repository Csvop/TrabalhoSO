

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

Nossa máquina virtual tem CPU e Memória.

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

Antes de começar, você vai precisar ter instalado o [Java](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html) e o [Git](https://git-scm.com) localmente no seu computador. 
Além disto é bom ter um editor para trabalhar com o código como [VSCode](https://code.visualstudio.com/).
Usando a IDE de browser [GitPod](https://gitpod.io/) não é preciso instalar nada localmente no seu computador.

## 🏃ㅤGetting Started (Seção implementação)

Para executar o Sistema execute o método `main()` do arquivo App.java.

Para escolher qual programa executar, remova o comentário referente ao programa desejado. Lembre de deixar apenas um programa descomentado. mais de um programa pode gerar erros.

## 🌿ㅤBranches

- ```main```: branch para desenvolvimento de features.

## 🚀ㅤFeatures

### OPCODES

Conjunto de instruções.

#### Instruções JUMP

- [x] JMP
- [x] JMPI
- [x] JMPIG
- [x] JMPIL
- [x] JMPIE
- [x] JMPIM
- [x] JMPIGM
- [x] JMPILM
- [x] JMPIEM

#### Instruções Aritméticas

- [x] ADD
- [x] ADDI
- [x] SUB
- [x] SUBI
- [x] MULT

#### Instruções de Movimentação

- [x] LDI
- [x] STD
- [x] STX
- [x] LDD
- [x] LDX
- [x] SWAP

#### Instruções Genréricas

- [x] TRAP
- [x] STOP
- [x] DATA

## Relatório

### Seção Programas:

Foram feitos todos os programas requeridos: PA, PB e PC. O programa PC parou de funcionar antes da entrega.

### Seção de Saídas

#### PA
<div align="center"><img src=https://cdn.discordapp.com/attachments/872968154723270657/880547929516015716/unknown.png /></div>

#### PB
<div align="center"><img src=https://cdn.discordapp.com/attachments/872968154723270657/880548097376260176/unknown.png /></div>

#### PC
não rodou :(


### Seção Considerações
A utilização do gradle em programas que precisam de input do terminal não funcionarão utilziando `gradle run`

## 🛠ㅤTecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Gradle](https://gradle.org/install/)


