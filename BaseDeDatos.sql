USE [master]
GO
/****** Object:  Database [MyTournament]    Script Date: 26/01/2021 03:00:51 p. m. ******/
CREATE DATABASE [MyTournament]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MyTournament', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\MyTournament.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MyTournament_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\MyTournament_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MyTournament].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MyTournament] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MyTournament] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MyTournament] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MyTournament] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MyTournament] SET ARITHABORT OFF 
GO
ALTER DATABASE [MyTournament] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MyTournament] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MyTournament] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MyTournament] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MyTournament] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MyTournament] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MyTournament] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MyTournament] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MyTournament] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MyTournament] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MyTournament] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MyTournament] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MyTournament] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MyTournament] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MyTournament] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MyTournament] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MyTournament] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MyTournament] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [MyTournament] SET  MULTI_USER 
GO
ALTER DATABASE [MyTournament] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MyTournament] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MyTournament] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MyTournament] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MyTournament] SET DELAYED_DURABILITY = DISABLED 
GO
USE [MyTournament]
GO
/****** Object:  Table [dbo].[Equipos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Equipos](
	[IDEquipo] [int] NOT NULL,
	[NombreEquipo] [varchar](20) NOT NULL,
	[IDTorneo] [int] NOT NULL,
 CONSTRAINT [PK_Equipos] PRIMARY KEY CLUSTERED 
(
	[IDEquipo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Fotos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Fotos](
	[IDFoto] [int] IDENTITY(1,1) NOT NULL,
	[Direccion] [varchar](200) NULL,
	[IDTorneo] [int] NULL,
 CONSTRAINT [PK_Fotos] PRIMARY KEY CLUSTERED 
(
	[IDFoto] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[GolesXUsuarioXPartidos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[GolesXUsuarioXPartidos](
	[IDUsuario] [int] NOT NULL,
	[IDPartido] [int] NOT NULL,
	[CantidadGoles] [int] NOT NULL,
	[IDTorneo] [int] NOT NULL,
	[TorneoActivo] [bit] NULL,
 CONSTRAINT [PK_GolesXPartidos] PRIMARY KEY CLUSTERED 
(
	[IDUsuario] ASC,
	[IDPartido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ListaDeEsperaParaTorneo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ListaDeEsperaParaTorneo](
	[IDUsuario] [int] NOT NULL,
	[IDTorneo] [int] NOT NULL,
	[Mensaje] [varchar](100) NOT NULL,
	[IDEquipo] [int] NOT NULL,
 CONSTRAINT [PK_ListaDeEsperaParaTorneo] PRIMARY KEY CLUSTERED 
(
	[IDUsuario] ASC,
	[IDTorneo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Noticias]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Noticias](
	[IDNoticia] [int] IDENTITY(1,1) NOT NULL,
	[Titulo] [varchar](80) NULL,
	[Descripcion] [varchar](300) NULL,
	[IDFoto] [int] NULL,
	[IDTorneo] [int] NULL,
	[Fecha] [date] NULL,
	[Destacada] [bit] NULL,
 CONSTRAINT [PK_Noticias] PRIMARY KEY CLUSTERED 
(
	[IDNoticia] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ParticipantesXTorneo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ParticipantesXTorneo](
	[IDUsuario] [int] NOT NULL,
	[IDTorneo] [int] NOT NULL,
	[IDEquipo] [int] NULL,
	[IDTipoParticipacion] [int] NOT NULL,
	[OrdenDeInsercion] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_TorneosParticipadosXUsuario_1] PRIMARY KEY CLUSTERED 
(
	[IDUsuario] ASC,
	[IDTorneo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Partidos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Partidos](
	[IDPartido] [int] IDENTITY(1,1) NOT NULL,
	[FechaDeEncuentro] [datetime] NULL,
	[JornadaDelTorneo] [int] NULL,
	[GolesLocal] [int] NULL,
	[GolesVisitante] [int] NULL,
	[IDTorneo] [int] NULL,
	[IDEquipo1] [int] NULL,
	[IDEquipo2] [int] NULL,
 CONSTRAINT [PK_Partidos] PRIMARY KEY CLUSTERED 
(
	[IDPartido] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TiposDeUsuarios]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TiposDeUsuarios](
	[IDTipo] [int] NOT NULL,
	[NombreTipo] [varchar](50) NOT NULL,
 CONSTRAINT [PK_TiposDeUsuarios] PRIMARY KEY CLUSTERED 
(
	[IDTipo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Torneos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Torneos](
	[IDTorneo] [int] IDENTITY(1,1) NOT NULL,
	[NombreTorneo] [varchar](20) NULL,
	[ContraseniaDeAdministrador] [varchar](20) NULL,
	[LinkParaUnirse] [varchar](40) NULL,
 CONSTRAINT [PK_Torneos] PRIMARY KEY CLUSTERED 
(
	[IDTorneo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuarios](
	[IDUsuario] [int] IDENTITY(1,1) NOT NULL,
	[NombreDeUsuario] [varchar](20) NOT NULL,
	[Contrasenia] [varchar](20) NOT NULL,
	[FechaDeNacimiento] [date] NOT NULL,
	[Email] [varchar](40) NOT NULL,
	[IDTipo] [int] NOT NULL,
 CONSTRAINT [PK_Usuarios_1] PRIMARY KEY CLUSTERED 
(
	[NombreDeUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (1, N'River Plate', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (2, N'Boca Juniors', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (3, N'Independiente', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (4, N'Racing', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (5, N'SanLorenzo', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (6, N'Huracan', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (7, N'Barcelona', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (8, N'Real Madrid', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (9, N'Atletico Madrid', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (10, N'Nueva Chicago', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (11, N'Bayern Munich', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (12, N'Leipzig', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (13, N'Dortmund', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (14, N'Wolfsburgo', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (15, N'Milan', 1)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (16, N'Colon', 9)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (17, N'Real betis', 8)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (18, N'Juventus', 6)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (19, N'Shalke04', 4)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (20, N'Atlanta', 7)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (21, N'Chacarita', 7)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (22, N'Manchester City', 2)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (23, N'Manchester United', 2)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (24, N'Chelsea', 2)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (25, N'Liverpool', 2)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (26, N'Wolverhampton', 3)
INSERT [dbo].[Equipos] ([IDEquipo], [NombreEquipo], [IDTorneo]) VALUES (27, N'Leeds United', 3)
GO
SET IDENTITY_INSERT [dbo].[Fotos] ON 

INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (1, N'https://es.wikipedia.org/wiki/F%C3%BAtbol#/media/Archivo:Football_iu_1996.jpg', 1)
INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (2, N'https://www.mundodeportivo.com/rf/image_large/GODO/MD/p7/Futbol/Imagenes/2020/08/20/Recortada/20200820-637334974852781493_20200820052502-kufF-U48291400994803D-980x554@MundoDeportivo-Web.jpg', 1)
INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (3, N'https://www.rushbet.co/blog/wp-content/uploads/2019/12/shutterstock_1113319799-1000x605.jpg', 1)
INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (4, N'https://a.espncdn.com/combiner/i?img=/media/motion/2020/0826/evc_FUTBOL_20200826_no_event_name_3a87d4b3_692a_4e1141/evc_FUTBOL_20200826_no_event_name_3a87d4b3_692a_4e1141.jpg', 2)
INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (5, N'https://www.telam.com.ar/advf/imagenes/2020/03/5e691ced2fd99_1004x565.jpg', 2)
INSERT [dbo].[Fotos] ([IDFoto], [Direccion], [IDTorneo]) VALUES (6, N'https://cdn.aarp.net/content/dam/aarp/entertainment/television/2017/07/1140-world-cup-trophy-ball-trivia-esp.imgcache.rev4469921697c064fd0c53617c437e67d8.jpg', 2)
SET IDENTITY_INSERT [dbo].[Fotos] OFF
GO
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (1, 1, 2, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (1, 4, 1, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (2, 1, 1, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (2, 3, 2, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (3, 2, 3, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (3, 4, 2, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (4, 2, 1, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (4, 3, 2, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (5, 5, 1, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (6, 5, 1, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (9, 13, 4, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (10, 12, 3, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (11, 13, 4, 1, 1)
INSERT [dbo].[GolesXUsuarioXPartidos] ([IDUsuario], [IDPartido], [CantidadGoles], [IDTorneo], [TorneoActivo]) VALUES (15, 12, 2, 1, 1)
GO
INSERT [dbo].[ListaDeEsperaParaTorneo] ([IDUsuario], [IDTorneo], [Mensaje], [IDEquipo]) VALUES (16, 1, N'Hola, me gustaria entrar en este torneo', 1)
INSERT [dbo].[ListaDeEsperaParaTorneo] ([IDUsuario], [IDTorneo], [Mensaje], [IDEquipo]) VALUES (17, 1, N'Hola, me gustaria entrar en este torneo', 2)
INSERT [dbo].[ListaDeEsperaParaTorneo] ([IDUsuario], [IDTorneo], [Mensaje], [IDEquipo]) VALUES (18, 1, N'Hola, me gustaria entrar en este torneo', 3)
GO
SET IDENTITY_INSERT [dbo].[Noticias] ON 

INSERT [dbo].[Noticias] ([IDNoticia], [Titulo], [Descripcion], [IDFoto], [IDTorneo], [Fecha], [Destacada]) VALUES (1, N'Suarez anota en su debut', N'El goleador uruguayo, nuevo refuerzo del Atletico de Madrid, marca un doblete en su debut y le de la victoria a los madrilenses.', 1, 1, CAST(N'2020-10-01' AS Date), 1)
INSERT [dbo].[Noticias] ([IDNoticia], [Titulo], [Descripcion], [IDFoto], [IDTorneo], [Fecha], [Destacada]) VALUES (2, N'Ansu Fati, la nueva promesa', N'El joven jugador nacionalizado español, llego a primera y la esta rompiendo marcando goles en todos los partidos.', 2, 1, CAST(N'2020-10-02' AS Date), 1)
INSERT [dbo].[Noticias] ([IDNoticia], [Titulo], [Descripcion], [IDFoto], [IDTorneo], [Fecha], [Destacada]) VALUES (3, N'La capitana del plantel mejicano festeja su triunfo', N'A traves de este triunfo, el conjunto mejicano consigue clasificar al mundial de futbol femenino', 3, 1, CAST(N'2020-10-08' AS Date), 0)
INSERT [dbo].[Noticias] ([IDNoticia], [Titulo], [Descripcion], [IDFoto], [IDTorneo], [Fecha], [Destacada]) VALUES (5, N'Real Madrid festeja ganar el campeonato', N'El equipo Real Madrid se consagra campeon del torneo Liga1 y asi se abrazan dirante el festejo', 5, 1, CAST(N'2020-10-18' AS Date), 1)
SET IDENTITY_INSERT [dbo].[Noticias] OFF
GO
SET IDENTITY_INSERT [dbo].[ParticipantesXTorneo] ON 

INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (1, 1, 1, 2, 1)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (1, 2, 25, 1, 19)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (1, 6, NULL, 1, 54)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (2, 1, 2, 2, 2)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (2, 2, 2, 1, 20)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (3, 1, 3, 2, 3)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (3, 4, 3, 1, 21)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (4, 1, 4, 3, 4)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (4, 4, 4, 1, 22)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (5, 1, 5, 2, 5)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (5, 5, 5, 1, 23)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (6, 1, 6, 3, 6)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (6, 5, 5, 1, 24)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (7, 1, 7, 2, 7)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (7, 7, 7, 1, 25)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (8, 1, 8, 2, 8)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (9, 1, 9, 2, 9)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (10, 1, 10, 2, 10)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (11, 1, 11, 2, 11)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (12, 1, 12, 2, 12)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (13, 1, 13, 3, 13)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (14, 1, 14, 3, 14)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (15, 1, 15, 2, 15)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (26, 1, 4, 1, 26)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (26, 5, NULL, 1, 27)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (41, 1, 1, 2, 16)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (45, 1, 1, 2, 17)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (45, 5, NULL, 1, 29)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (48, 1, 1, 2, 18)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (48, 2, NULL, 1, 31)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (48, 6, NULL, 1, 32)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (48, 7, NULL, 1, 33)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (48, 8, NULL, 1, 34)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (49, 1, NULL, 1, 35)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (49, 6, NULL, 1, 36)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (51, 1, NULL, 6, 41)
INSERT [dbo].[ParticipantesXTorneo] ([IDUsuario], [IDTorneo], [IDEquipo], [IDTipoParticipacion], [OrdenDeInsercion]) VALUES (51, 2, NULL, 1, 42)
SET IDENTITY_INSERT [dbo].[ParticipantesXTorneo] OFF
GO
SET IDENTITY_INSERT [dbo].[Partidos] ON 

INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (1, CAST(N'2020-04-05T15:30:00.000' AS DateTime), 1, 2, 1, 1, 1, 2)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (2, CAST(N'2020-11-10T16:00:00.000' AS DateTime), 1, 3, 1, 1, 3, 4)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (3, CAST(N'2020-03-27T14:20:00.000' AS DateTime), 2, 2, 2, 1, 2, 4)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (4, CAST(N'2020-07-05T00:00:00.000' AS DateTime), 2, 2, 1, 1, 3, 1)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (5, CAST(N'2020-07-04T00:00:00.000' AS DateTime), 1, 1, 1, 1, 5, 6)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (6, CAST(N'2020-04-23T00:00:00.000' AS DateTime), 1, 1, 1, 2, 7, 8)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (7, CAST(N'2020-04-24T00:00:00.000' AS DateTime), 1, 1, 3, 5, 13, 11)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (8, CAST(N'2020-04-25T00:00:00.000' AS DateTime), 1, 2, 3, 5, 12, 14)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (9, CAST(N'2020-04-26T00:00:00.000' AS DateTime), 2, 1, 2, 2, 10, 9)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (10, CAST(N'2020-11-21T00:00:00.000' AS DateTime), 1, 2, 1, 7, 20, 21)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (11, CAST(N'2020-11-25T00:00:00.000' AS DateTime), 2, 0, 0, 1, 11, 13)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (12, CAST(N'2020-11-30T00:00:00.000' AS DateTime), 1, 3, 2, 1, 10, 15)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (13, CAST(N'2020-12-01T00:00:00.000' AS DateTime), 1, 4, 4, 1, 11, 9)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (14, CAST(N'2020-12-01T00:00:00.000' AS DateTime), 3, -1, -1, 1, 14, 6)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (15, CAST(N'2020-12-02T00:00:00.000' AS DateTime), 3, -1, -1, 1, 12, 3)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (16, CAST(N'2020-12-02T00:00:00.000' AS DateTime), 3, -1, -1, 1, 1, 7)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (17, CAST(N'2020-12-03T19:43:00.000' AS DateTime), 3, -1, -1, 1, 8, 5)
INSERT [dbo].[Partidos] ([IDPartido], [FechaDeEncuentro], [JornadaDelTorneo], [GolesLocal], [GolesVisitante], [IDTorneo], [IDEquipo1], [IDEquipo2]) VALUES (18, CAST(N'2020-11-18T18:40:00.000' AS DateTime), 2, 0, 0, 1, 5, 15)
SET IDENTITY_INSERT [dbo].[Partidos] OFF
GO
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (1, N'Usuario')
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (2, N'Jugador')
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (3, N'Capitan')
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (4, N'Creador')
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (5, N'Administrador')
INSERT [dbo].[TiposDeUsuarios] ([IDTipo], [NombreTipo]) VALUES (6, N'GranAdministrador')
GO
SET IDENTITY_INSERT [dbo].[Torneos] ON 

INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (1, N'Liga1', N'Admin1', N'231')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (2, N'Liga2', N'Admin2', N'234')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (3, N'Liga3', N'Admin3', N'567')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (4, N'Liga4', N'Admin4', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (5, N'Liga5', N'Admin5', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (6, N'Liga6', N'Admin6', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (7, N'Liga7', N'Admin7', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (8, N'Liga8', N'Admin8', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (9, N'Liga9', N'Admin9', N'123')
INSERT [dbo].[Torneos] ([IDTorneo], [NombreTorneo], [ContraseniaDeAdministrador], [LinkParaUnirse]) VALUES (10, N'Liga10', N'Admin10', N'123')
SET IDENTITY_INSERT [dbo].[Torneos] OFF
GO
SET IDENTITY_INSERT [dbo].[Usuarios] ON 

INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (45, N'Abelardo', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Abe@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (49, N'AlanBursztyn', N'alaninsta', CAST(N'1970-01-01' AS Date), N'alan.bursztyn@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (26, N'Albert', N'fsfaft', CAST(N'2003-05-08' AS Date), N'efceqc@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (22, N'Alberto', N'fsfaft', CAST(N'2003-05-08' AS Date), N'efceqc@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (2, N'Ale', N'ale123', CAST(N'2003-11-11' AS Date), N'ale@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (46, N'Alejandro', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'sdsd@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (18, N'AnabelaLopez', N'356764', CAST(N'1998-03-05' AS Date), N'ani@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (48, N'Anastasio', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'elanas@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (15, N'Bochini', N'elbocha5', CAST(N'1954-06-14' AS Date), N'elBocha@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (14, N'Clemente Rodriguez', N'clemenBoca', CAST(N'1998-12-26' AS Date), N'clemen@gmail.com|', 3)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (5, N'Cristiano', N'gol123', CAST(N'1990-12-10' AS Date), N'gol@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (1, N'DamianGluk', N'Dami123', CAST(N'2003-05-08' AS Date), N'damigluk@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (47, N'Dan', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Dani@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (7, N'Diego Armando', N'fallguys123', CAST(N'2020-10-08' AS Date), N'fall@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (41, N'EzequielMartin', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Eze@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (34, N'fq3edeqfaf', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'eqfefe@.', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (3, N'Gabo', N'gabo123', CAST(N'2004-11-11' AS Date), N'jojo@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (39, N'Gianluca', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'gianlu@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (38, N'Gonzalo88', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Gonzi@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (17, N'GonzaloRamos', N'32452', CAST(N'2000-04-23' AS Date), N'GonzaloRamos@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (51, N'GranAdministrador', N'administrador', CAST(N'2003-05-08' AS Date), N'Admin@gmail.com', 6)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (35, N'Javier', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Ja@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (4, N'Jorge', N'dale123', CAST(N'1999-11-11' AS Date), N'dale@gmail.com', 3)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (6, N'Lionel', N'camp123', CAST(N'2000-12-10' AS Date), N'camp@gmail.com', 3)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (16, N'Lucas', N'Luki32', CAST(N'2002-08-12' AS Date), N'Luki@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (37, N'Lucas45', N'aaaaaa', CAST(N'1970-01-01' AS Date), N'Luki349@.', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (12, N'Luis enrique', N'luis44', CAST(N'1954-08-04' AS Date), N'luisEnriq@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (10, N'Panenka', N'panenka32', CAST(N'1922-01-10' AS Date), N'panenka@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (13, N'Pele', N'pele400', CAST(N'1940-10-23' AS Date), N'pele@gmail.com', 3)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (9, N'Platini', N'plati888', CAST(N'1944-05-22' AS Date), N'platini@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (11, N'Riquelme', N'jroman10', CAST(N'2004-02-28' AS Date), N'juanRoman@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (36, N'Roberto', N'elrober', CAST(N'2020-12-01' AS Date), N'eltobo@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (50, N'Rodolfolonguer', N'aaaaaa', CAST(N'2003-08-05' AS Date), N'rodel@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (8, N'Samuel Eto', N'e2e', CAST(N'2002-03-09' AS Date), N'sxw@gmail.com', 2)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (29, N'Tobias2', N'eltobo123', CAST(N'2020-12-01' AS Date), N'eltobo@gmail.com', 1)
INSERT [dbo].[Usuarios] ([IDUsuario], [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email], [IDTipo]) VALUES (28, N'Tobiasalvarez', N'aaaaaa', CAST(N'2020-12-01' AS Date), N'eltobo@gmail.com', 1)
SET IDENTITY_INSERT [dbo].[Usuarios] OFF
GO
ALTER TABLE [dbo].[Equipos]  WITH CHECK ADD  CONSTRAINT [FK_Equipos_Torneos] FOREIGN KEY([IDTorneo])
REFERENCES [dbo].[Torneos] ([IDTorneo])
GO
ALTER TABLE [dbo].[Equipos] CHECK CONSTRAINT [FK_Equipos_Torneos]
GO
ALTER TABLE [dbo].[GolesXUsuarioXPartidos]  WITH CHECK ADD  CONSTRAINT [FK_GolesXPartidos_Partidos] FOREIGN KEY([IDPartido])
REFERENCES [dbo].[Partidos] ([IDPartido])
GO
ALTER TABLE [dbo].[GolesXUsuarioXPartidos] CHECK CONSTRAINT [FK_GolesXPartidos_Partidos]
GO
ALTER TABLE [dbo].[ListaDeEsperaParaTorneo]  WITH CHECK ADD  CONSTRAINT [FK_ListaDeEsperaParaTorneo_Torneos] FOREIGN KEY([IDTorneo])
REFERENCES [dbo].[Torneos] ([IDTorneo])
GO
ALTER TABLE [dbo].[ListaDeEsperaParaTorneo] CHECK CONSTRAINT [FK_ListaDeEsperaParaTorneo_Torneos]
GO
ALTER TABLE [dbo].[Noticias]  WITH CHECK ADD  CONSTRAINT [FK_Noticias_Torneos] FOREIGN KEY([IDTorneo])
REFERENCES [dbo].[Torneos] ([IDTorneo])
GO
ALTER TABLE [dbo].[Noticias] CHECK CONSTRAINT [FK_Noticias_Torneos]
GO
ALTER TABLE [dbo].[ParticipantesXTorneo]  WITH CHECK ADD  CONSTRAINT [FK_TorneosParticipadosXUsuario_Torneos1] FOREIGN KEY([IDTorneo])
REFERENCES [dbo].[Torneos] ([IDTorneo])
GO
ALTER TABLE [dbo].[ParticipantesXTorneo] CHECK CONSTRAINT [FK_TorneosParticipadosXUsuario_Torneos1]
GO
ALTER TABLE [dbo].[Partidos]  WITH CHECK ADD  CONSTRAINT [FK_Partidos_Torneos] FOREIGN KEY([IDTorneo])
REFERENCES [dbo].[Torneos] ([IDTorneo])
GO
ALTER TABLE [dbo].[Partidos] CHECK CONSTRAINT [FK_Partidos_Torneos]
GO
ALTER TABLE [dbo].[Usuarios]  WITH CHECK ADD  CONSTRAINT [FK_Usuarios_TiposDeUsuarios] FOREIGN KEY([IDTipo])
REFERENCES [dbo].[TiposDeUsuarios] ([IDTipo])
GO
ALTER TABLE [dbo].[Usuarios] CHECK CONSTRAINT [FK_Usuarios_TiposDeUsuarios]
GO
/****** Object:  StoredProcedure [dbo].[CambiarEquipoFavorito]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[CambiarEquipoFavorito]
	@IDEquipo int,
	@IDUsuario int,
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	Update ParticipantesXTorneo SET IDEquipo = @IDEquipo where IDUsuario = @IDUsuario and IDTorneo = @IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[DeleteTorneoSeguidoPorUsuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[DeleteTorneoSeguidoPorUsuario]
	-- Add the parameters for the stored procedure here
	@IDTorneo int,
	@IDUsuario int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	delete from ParticipantesXTorneo where IDUsuario = @IDUsuario and IDTorneo = @IDTorneo
	
END
GO
/****** Object:  StoredProcedure [dbo].[InsertarTorneoSeguidoPorUsuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[InsertarTorneoSeguidoPorUsuario]
	-- Add the parameters for the stored procedure here
	@IDTorneo int,
	@IDUsuario int,
	@IDEquipoFavorito int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	insert into ParticipantesXTorneo(IDUsuario,IDTorneo,IDEquipo,IDTipoParticipacion) values (@IDUsuario,@IDTorneo,@IDEquipoFavorito,1)
	SELECT IsNull(SCOPE_IDENTITY(), 0) as Respuesta
	
END
GO
/****** Object:  StoredProcedure [dbo].[InsertarUsuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[InsertarUsuario]
	-- Add the parameters for the stored procedure here
	@NombreUsuario varchar(20),
	@Contrasenia varchar(20),
	@FechaNacimiento date,
	@Email varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	INSERT into [Usuarios] ( [NombreDeUsuario], [Contrasenia], [FechaDeNacimiento], [Email],[IDTipo]) VALUES (@NombreUsuario, @Contrasenia, @FechaNacimiento, @Email,1)
	SELECT IsNull(SCOPE_IDENTITY(), 0) as Respuesta
END
GO
/****** Object:  StoredProcedure [dbo].[TraerEquipoPorIDEquipo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerEquipoPorIDEquipo] 
	-- Add the parameters for the stored procedure here
	@IDEquipo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
SELECT Equipos.*,
(Select Count(GolesLocal) from Partidos where Partidos.IDEquipo1 = Equipos.IDEquipo)+
(Select Count(GolesVisitante)from Partidos where Partidos.IDEquipo2 = Equipos.IDEquipo) as GolesAFavor,

(Select Count(GolesVisitante) from Partidos where Partidos.IDEquipo1 = Equipos.IDEquipo)+
(Select Count(GolesLocal)from Partidos where Partidos.IDEquipo2 = Equipos.IDEquipo) as GolesEnContra,

(select (ISNULL((select SUM(3) from Partidos where IDEquipo1 = IDEquipo and GolesLocal > GolesVisitante), 0 )) +
(ISNULL((select SUM(3) from Partidos where IDEquipo2 = IDEquipo and GolesVisitante > GolesLocal),0))+
(ISNULL((select SUM(1) from Partidos where GolesLocal >-1 and (IDEquipo2 = IDEquipo or IDEquipo1 = IDEquipo) and GolesVisitante = GolesLocal),0)))as Puntos,

(select (ISNULL((select Count(IDPartido) from Partidos where GolesLocal >-1 and (IDEquipo2 = IDEquipo or IDEquipo1 = IDEquipo)),0))) as PartidosJugados
FROM Equipos 
where Equipos.IDEquipo = @IDEquipo order by Puntos desc, GolesAFavor desc
END
GO
/****** Object:  StoredProcedure [dbo].[TraerGolesXusuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerGolesXusuario] 
	-- Add the parameters for the stored procedure here
	@IDPartido int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
select Usuarios.NombreDeUsuario, GolesXUsuarioXPartidos.*,ParticipantesXTorneo.IDEquipo from GolesXUsuarioXPartidos
INNER join ParticipantesXTorneo on ParticipantesXTorneo.IDUsuario = GolesXUsuarioXPartidos.IDUsuario
INNER join Usuarios on Usuarios.IDUsuario = GolesXUsuarioXPartidos.IDUsuario
where IDPartido = @IDPartido and ParticipantesXTorneo.IDTorneo = GolesXUsuarioXPartidos.IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerInfoDeTorneo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerInfoDeTorneo]
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select
	(select count(IDUsuario) from ParticipantesXTorneo where IDTorneo = @IDTorneo and IDTipoParticipacion = 1) as Seguidores,
	(select count(IDNoticia) from Noticias where IDTorneo = @IDTorneo) as Noticias
END
GO
/****** Object:  StoredProcedure [dbo].[TraerJornadasPorTorneo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerJornadasPorTorneo]
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT Distinct JornadaDelTorneo FROM Partidos where Partidos.IDTorneo = +@IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerJugadoresXEquipos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerJugadoresXEquipos] 
	-- Add the parameters for the stored procedure here
	@IDEquipo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
			select Usuarios.*,
	IsNull((select sum(CantidadGoles) from GolesXUsuarioXPartidos where IDUsuario = Usuarios.IDUsuario), 0) As CantidadGoles
	from Usuarios
	inner join ParticipantesXTorneo on ParticipantesXTorneo.IDUsuario = Usuarios.IDUsuario
	where IDEquipo = @IDEquipo and (IDTipoParticipacion = 2 or IDTipoParticipacion = 3)
END
GO
/****** Object:  StoredProcedure [dbo].[TraerListaDeEspera]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerListaDeEspera]
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select ListaDeEsperaParaTorneo.IDUsuario,Usuarios.NombreDeUsuario,ListaDeEsperaParaTorneo.IDEquipo, Equipos.NombreEquipo,Mensaje from ListaDeEsperaParaTorneo 
inner join Usuarios on Usuarios.IDUsuario = ListaDeEsperaParaTorneo.IDUsuario
inner join Equipos on Equipos.IDEquipo = ListaDeEsperaParaTorneo.IDEquipo
where ListaDeEsperaParaTorneo.IDTorneo = @IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerListaGoleadores]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerListaGoleadores] 
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select Usuarios.IDUsuario,NombreDeUsuario, Equipos.NombreEquipo,sum(CantidadGoles) as CantidadGoles from Usuarios
	inner join ParticipantesXTorneo on Usuarios.IDUsuario = ParticipantesXTorneo.IDUsuario
	inner join Equipos on Equipos.IDEquipo = ParticipantesXTorneo.IDEquipo
	inner join GolesXUsuarioXPartidos on Usuarios.IDUsuario = GolesXUsuarioXPartidos.IDUsuario 
	where ParticipantesXTorneo.IDTorneo = @IDTorneo and (IDTipoParticipacion = 2 or IDTipoParticipacion = 3) and TorneoActivo = 1
	group by Usuarios.IDUsuario,NombreDeUsuario, Equipos.NombreEquipo
	order by CantidadGoles desc
END
GO
/****** Object:  StoredProcedure [dbo].[TraerListaPosiciones]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[TraerListaPosiciones]
    -- Add the parameters for the stored procedure here
    @IDTorneo int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    -- Insert statements for procedure here
    SELECT Equipos.*,
(Select Count(GolesLocal) from Partidos where Partidos.IDEquipo1 = Equipos.IDEquipo)+
(Select Count(GolesVisitante)from Partidos where Partidos.IDEquipo2 = Equipos.IDEquipo) as GolesAFavor,

(Select Count(GolesVisitante) from Partidos where Partidos.IDEquipo1 = Equipos.IDEquipo)+
(Select Count(GolesLocal)from Partidos where Partidos.IDEquipo2 = Equipos.IDEquipo) as GolesEnContra,

(select (ISNULL((select SUM(3) from Partidos where IDEquipo1 = IDEquipo and GolesLocal > GolesVisitante), 0 )) +
(ISNULL((select SUM(3) from Partidos where IDEquipo2 = IDEquipo and GolesVisitante > GolesLocal),0))+
(ISNULL((select SUM(1) from Partidos where GolesLocal >-1 and (IDEquipo2 = IDEquipo or IDEquipo1 = IDEquipo) and GolesVisitante = GolesLocal),0)))as Puntos,

(select (ISNULL((select Count(IDPartido) from Partidos where GolesLocal >-1 and (IDEquipo2 = IDEquipo or IDEquipo1 = IDEquipo)),0))) as PartidosJugados
FROM Equipos 
where Equipos.IDTorneo = @IDTorneo order by Puntos desc, GolesAFavor desc
END
GO
/****** Object:  StoredProcedure [dbo].[TraerNoticiasPorTorneo]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerNoticiasPorTorneo] 
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	Select * from Noticias where IDTorneo = @IDTorneo order by Destacada DESC
END
GO
/****** Object:  StoredProcedure [dbo].[TraerPartidos]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerPartidos]
	-- Add the parameters for the stored procedure here
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT Partidos.*,EquipoLocal.NombreEquipo As NombreEquipoLocal, EquipoVisitante.NombreEquipo AS NombreEquipoVisitante from Partidos
	LEFT JOIN Equipos EquipoLocal ON Partidos.IDEquipo1 = EquipoLocal.IDEquipo
    LEFT JOIN Equipos EquipoVisitante ON Partidos.IDEquipo2 = EquipoVisitante.IDEquipo
	order by JornadaDelTorneo DESC
END
GO
/****** Object:  StoredProcedure [dbo].[TraerPartidosPorJornada]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerPartidosPorJornada]
	-- Add the parameters for the stored procedure here
	@IDJornada int,
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT Partidos.*,EquipoLocal.NombreEquipo As NombreEquipoLocal, EquipoVisitante.NombreEquipo AS NombreEquipoVisitante
          FROM Partidos INNER JOIN Equipos EquipoLocal ON Partidos.IDEquipo1 = EquipoLocal.IDEquipo
          INNER JOIN Equipos EquipoVisitante ON Partidos.IDEquipo2 = EquipoVisitante.IDEquipo
          where Partidos.JornadaDelTorneo = @IDJornada and Partidos.IDTorneo = + @IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerTorneoPorID]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerTorneoPorID]
	-- Add the parameters for the stored procedure here
	@IDTorneo int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * FROM Torneos where Torneos.IDTorneo = @IDTorneo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerTorneosParticipadosPorUsuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerTorneosParticipadosPorUsuario] 
	-- Add the parameters for the stored procedure here
	@IDUsuario int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select Torneos.*,IDTipoParticipacion, IsNULL(IDEquipo,0) as IDEquipo from Torneos
inner join ParticipantesXTorneo on ParticipantesXTorneo.IDTorneo = Torneos.IDTorneo
where IDUsuario = @IDUsuario and IDTipoParticipacion > 1
END
GO
/****** Object:  StoredProcedure [dbo].[TraerTorneosPorNombre]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[TraerTorneosPorNombre]
    -- Add the parameters for the stored procedure here
    @Nombre varchar(30),
	@IDUsuario int
AS
BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    -- Insert statements for procedure here\

	IF @Nombre = '()' 
BEGIN
SELECT Torneos.*,CASE WHEN ParticipantesXTorneo.IDUsuario IS NOT NULL THEN IDTipoParticipacion ELSE 0 END AS IDParticipacion
,ISNULL(IDEquipo,0) as IDEquipo from Torneos 
LEFT JOIN ParticipantesXTorneo on ParticipantesXTorneo.IDTorneo = Torneos.IDTorneo AND ParticipantesXTorneo.IDUsuario = @IDUsuario
END
ELSE
BEGIN
SELECT Torneos.*, CASE WHEN ParticipantesXTorneo.IDUsuario IS NOT NULL THEN IDTipoParticipacion ELSE 0 END AS IDParticipacion
,ISNULL(IDEquipo,0) as IDEquipo from Torneos 
LEFT JOIN ParticipantesXTorneo ON Torneos.IDTorneo = ParticipantesXTorneo.IDTorneo AND ParticipantesXTorneo.IDUsuario = @IDUsuario
where NombreTorneo LIKE '%'+@Nombre+'%'
order by Torneos.NombreTorneo ASC
END
END
GO
/****** Object:  StoredProcedure [dbo].[TraerTorneosSeguidosPorUsuario]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerTorneosSeguidosPorUsuario] 
	-- Add the parameters for the stored procedure here
	@IDUsuario int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select Torneos.*,IDTipoParticipacion, IsNULL(IDEquipo,0) as IDEquipo from Torneos
inner join ParticipantesXTorneo on ParticipantesXTorneo.IDTorneo = Torneos.IDTorneo
where IDUsuario = @IDUsuario and IDTipoParticipacion = 1
END
GO
/****** Object:  StoredProcedure [dbo].[TraerUsuarioPorNombreContrasenia]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerUsuarioPorNombreContrasenia] 
	-- Add the parameters for the stored procedure here
	@NombreUsuario varchar(20),
	@Contrasenia varchar(20)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	Select Usuarios.*,IsNull(sum(CantidadGoles), 0) As CantidadGoles
	from Usuarios LEFT JOIN GolesXUsuarioXPartidos on Usuarios.IDUsuario = GolesXUsuarioXPartidos.IDUsuario 
	where Usuarios.NombreDeUsuario = @NombreUsuario COLLATE Modern_Spanish_100_CS_AI and Usuarios.Contrasenia = @Contrasenia COLLATE Modern_Spanish_100_CS_AI
	group by Usuarios.IDUsuario,NombreDeUsuario,Contrasenia,FechaDeNacimiento,Email,IDTipo
END
GO
/****** Object:  StoredProcedure [dbo].[TraerUsuariosPorID]    Script Date: 26/01/2021 03:00:51 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[TraerUsuariosPorID] 
	-- Add the parameters for the stored procedure here
	@IDUsuario int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
		Select Usuarios.*,IsNull(sum(CantidadGoles), 0) As CantidadGoles from Usuarios FULL OUTER JOIN 
	GolesXUsuarioXPartidos on Usuarios.IDUsuario = GolesXUsuarioXPartidos.IDUsuario where Usuarios.IDUsuario = @IDUsuario
	group by Usuarios.IDUsuario,NombreDeUsuario,Contrasenia,FechaDeNacimiento,Email,IDTipo
END
GO
USE [master]
GO
ALTER DATABASE [MyTournament] SET  READ_WRITE 
GO
