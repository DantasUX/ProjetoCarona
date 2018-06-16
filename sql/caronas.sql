-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 16-Jun-2018 às 04:40
-- Versão do servidor: 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `caronas`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `carona`
--

CREATE TABLE `carona` (
  `id` int(11) NOT NULL,
  `origem` varchar(100) NOT NULL,
  `destino` varchar(100) NOT NULL,
  `cidade` varchar(50) DEFAULT NULL,
  `data` date NOT NULL,
  `dataVolta` date DEFAULT NULL,
  `hora` time NOT NULL,
  `vagas` int(11) DEFAULT NULL,
  `municipal` tinyint(4) NOT NULL,
  `minimoCaroneiros` int(11) NOT NULL,
  `preferencial` tinyint(1) NOT NULL DEFAULT '0',
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `carona`
--

INSERT INTO `carona` (`id`, `origem`, `destino`, `cidade`, `data`, `dataVolta`, `hora`, `vagas`, `municipal`, `minimoCaroneiros`, `preferencial`, `idUsuario`) VALUES
(1, 'Campina Grande', 'João Pessoa', NULL, '2013-06-20', NULL, '12:00:00', 0, 0, 0, 0, 1),
(2, 'João Pessoa', 'Campina Grande', NULL, '2013-06-30', NULL, '16:00:00', 1, 0, 0, 1, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `interesse`
--

CREATE TABLE `interesse` (
  `id` int(11) NOT NULL,
  `origem` varchar(100) NOT NULL,
  `destino` varchar(100) NOT NULL,
  `data` date DEFAULT NULL,
  `horaInicio` time DEFAULT NULL,
  `horaFim` time DEFAULT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `mensagem`
--

CREATE TABLE `mensagem` (
  `id` int(11) NOT NULL,
  `texto` varchar(1000) NOT NULL,
  `lida` tinyint(4) NOT NULL DEFAULT '0',
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `presenca`
--

CREATE TABLE `presenca` (
  `id` int(11) NOT NULL,
  `carona` int(11) NOT NULL,
  `motorista` int(11) NOT NULL,
  `loginCaroneiro` varchar(50) NOT NULL,
  `caroneiroFaltou` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicitacao`
--

CREATE TABLE `solicitacao` (
  `id` int(11) NOT NULL,
  `caroneiro` int(11) NOT NULL,
  `motorista` int(11) DEFAULT NULL,
  `carona` int(11) NOT NULL,
  `pontosSugeridos` varchar(1000) DEFAULT NULL,
  `respostaPontosSugeridos` varchar(1000) DEFAULT NULL,
  `pontoEncontro` varchar(100) DEFAULT NULL,
  `solicitacaoAceita` tinyint(1) NOT NULL DEFAULT '0',
  `solicitacaoRejeitada` int(11) NOT NULL DEFAULT '0',
  `solicitacaoDesistida` tinyint(1) NOT NULL DEFAULT '0',
  `segura` tinyint(1) NOT NULL DEFAULT '0',
  `naoFuncionou` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `solicitacao`
--

INSERT INTO `solicitacao` (`id`, `caroneiro`, `motorista`, `carona`, `pontosSugeridos`, `respostaPontosSugeridos`, `pontoEncontro`, `solicitacaoAceita`, `solicitacaoRejeitada`, `solicitacaoDesistida`, `segura`, `naoFuncionou`) VALUES
(1, 2, 1, 1, NULL, NULL, NULL, 1, 0, 0, 1, 0),
(2, 3, 1, 1, NULL, NULL, NULL, 1, 0, 0, 0, 1),
(3, 4, 1, 1, NULL, NULL, NULL, 1, 0, 0, 1, 0),
(4, 2, 1, 2, NULL, NULL, NULL, 1, 0, 0, 0, 0),
(5, 4, 1, 2, NULL, NULL, NULL, 1, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(50) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `endereco` varchar(100) DEFAULT NULL,
  `email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id`, `login`, `senha`, `nome`, `endereco`, `email`) VALUES
(1, 'mark', 'm@rk', 'Mark Zuckerberg', 'Palo Alto, California', 'mark@facebook.com'),
(2, 'bill', 'bilz@o', 'William Henry Gates III', 'Medina, Washington', 'billzin@gmail.com'),
(3, 'vader', 'd4rth', 'Anakin Skywalker', 'Death Star I', 'darthvader@empire.com'),
(4, 'anakin', 'd4rth', 'Anakin Skywalker', 'Dark Side', 'anakin@darkside.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carona`
--
ALTER TABLE `carona`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idUsuario` (`idUsuario`);

--
-- Indexes for table `interesse`
--
ALTER TABLE `interesse`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario.id` (`idUsuario`);

--
-- Indexes for table `mensagem`
--
ALTER TABLE `mensagem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario.id` (`idUsuario`);

--
-- Indexes for table `presenca`
--
ALTER TABLE `presenca`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `solicitacao`
--
ALTER TABLE `solicitacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `caroneiro` (`caroneiro`),
  ADD KEY `motorista` (`motorista`),
  ADD KEY `idCarona` (`carona`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carona`
--
ALTER TABLE `carona`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `interesse`
--
ALTER TABLE `interesse`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `mensagem`
--
ALTER TABLE `mensagem`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `presenca`
--
ALTER TABLE `presenca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `solicitacao`
--
ALTER TABLE `solicitacao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `carona`
--
ALTER TABLE `carona`
  ADD CONSTRAINT `carona_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`);

--
-- Limitadores para a tabela `solicitacao`
--
ALTER TABLE `solicitacao`
  ADD CONSTRAINT `solicitacao_ibfk_1` FOREIGN KEY (`caroneiro`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `solicitacao_ibfk_2` FOREIGN KEY (`motorista`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `solicitacao_ibfk_3` FOREIGN KEY (`carona`) REFERENCES `carona` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
