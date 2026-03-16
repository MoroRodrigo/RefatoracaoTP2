# BuildPipeline Refactoring Kata

Este projeto é uma refatoração do sistema legado de Pipeline de Build, realizada como parte do Teste de Performance 2 (TP2).

## Melhorias Realizadas
- **Extração de Métodos:** O método `run` foi decomposto em etapas claras (Testes, Deploy e Notificação).
- **Encapsulamento:** Introdução da classe `PipelineResult` para gerenciar o estado da execução.
- **Legibilidade:** Remoção de aninhamento excessivo (Arrow Anti-pattern) usando cláusulas de guarda.
- **Expressividade:** Substituição de lógicas complexas por métodos com nomes que explicam a intenção do negócio.

## Como Executar
1. Importe o projeto no IntelliJ como um projeto Maven/Java.
2. Execute os testes em `src/test/java/org/sammancoaching/PipelineTest.java` para validar a integridade do sistema.