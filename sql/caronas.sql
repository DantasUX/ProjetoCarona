-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 22-Abr-2018 às 03:24
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
  `data` date NOT NULL,
  `hora` time NOT NULL,
  `vagas` int(11) NOT NULL,
  `segura` tinyint(1) NOT NULL DEFAULT '0',
  `funcionou` tinyint(1) NOT NULL DEFAULT '1',
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `carona`
--

INSERT INTO `carona` (`id`, `origem`, `destino`, `data`, `hora`, `vagas`, `segura`, `funcionou`, `idUsuario`) VALUES
(1, 'João Pessoa', 'Campina Grande', '2013-06-23', '16:00:00', 3, 0, 1, 1),
(2, 'Rio de Janeiro', 'São Paulo', '2013-05-31', '08:00:00', 2, 0, 1, 1),
(3, 'João Pessoa', 'Campina Grande', '2026-11-25', '06:59:00', 3, 0, 1, 1);

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

--
-- Extraindo dados da tabela `presenca`
--

INSERT INTO `presenca` (`id`, `carona`, `motorista`, `loginCaroneiro`, `caroneiroFaltou`) VALUES
(1, 1, 1, 'steve', 1),
(2, 3, 1, 'steve', 0);

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
  `solicitacaoDesistida` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `solicitacao`
--

INSERT INTO `solicitacao` (`id`, `caroneiro`, `motorista`, `carona`, `pontosSugeridos`, `respostaPontosSugeridos`, `pontoEncontro`, `solicitacaoAceita`, `solicitacaoRejeitada`, `solicitacaoDesistida`) VALUES
(1, 2, 1, 1, 'Acude Velho; Hiper Bompreco', 'Acude Velho;Parque da Crianca', 'Acude Velho', 1, 0, 1),
(2, 2, 1, 2, NULL, NULL, NULL, 0, 1, 0),
(3, 2, 1, 3, NULL, NULL, NULL, 1, 0, 0);

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
(2, 'steve', '5t3v3', 'Steven Paul Jobs', 'Palo Alto, California', 'jobs@apple.com'),
(3, 'bill', 'severino', 'William Henry Gates III', 'Medina, Washington', 'billzin@msn.com');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `presenca`
--
ALTER TABLE `presenca`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `solicitacao`
--
ALTER TABLE `solicitacao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
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
