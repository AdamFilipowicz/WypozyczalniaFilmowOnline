/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2016 (13.0.4001)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Standard Edition
    Target Database Engine Type : Standalone SQL Server
*/
USE [Wypozyczalnia]
GO
/****** Object:  ApplicationRole [users]    Script Date: 13.01.2018 18:09:36 ******/
/* To avoid disclosure of passwords, the password is generated in script. */
declare @idx as int
declare @randomPwd as nvarchar(64)
declare @rnd as float
select @idx = 0
select @randomPwd = N''
select @rnd = rand((@@CPU_BUSY % 100) + ((@@IDLE % 100) * 100) + 
       (DATEPART(ss, GETDATE()) * 10000) + ((cast(DATEPART(ms, GETDATE()) as int) % 100) * 1000000))
while @idx < 64
begin
   select @randomPwd = @randomPwd + char((cast((@rnd * 83) as int) + 43))
   select @idx = @idx + 1
select @rnd = rand()
end
declare @statement nvarchar(4000)
select @statement = N'CREATE APPLICATION ROLE [users] WITH DEFAULT_SCHEMA = [dbo], ' + N'PASSWORD = N' + QUOTENAME(@randomPwd,'''')
EXEC dbo.sp_executesql @statement
GO
/****** Object:  ApplicationRole [workers]    Script Date: 13.01.2018 18:09:36 ******/
/* To avoid disclosure of passwords, the password is generated in script. */
declare @idx as int
declare @randomPwd as nvarchar(64)
declare @rnd as float
select @idx = 0
select @randomPwd = N''
select @rnd = rand((@@CPU_BUSY % 100) + ((@@IDLE % 100) * 100) + 
       (DATEPART(ss, GETDATE()) * 10000) + ((cast(DATEPART(ms, GETDATE()) as int) % 100) * 1000000))
while @idx < 64
begin
   select @randomPwd = @randomPwd + char((cast((@rnd * 83) as int) + 43))
   select @idx = @idx + 1
select @rnd = rand()
end
declare @statement nvarchar(4000)
select @statement = N'CREATE APPLICATION ROLE [workers] WITH DEFAULT_SCHEMA = [dbo], ' + N'PASSWORD = N' + QUOTENAME(@randomPwd,'''')
EXEC dbo.sp_executesql @statement
GO
/****** Object:  User [dba]    Script Date: 13.01.2018 18:09:36 ******/
CREATE USER [dba] FOR LOGIN [dba] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [userAccount]    Script Date: 13.01.2018 18:09:36 ******/
CREATE USER [userAccount] FOR LOGIN [userAccount] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [workerAccount]    Script Date: 13.01.2018 18:09:36 ******/
CREATE USER [workerAccount] FOR LOGIN [workerAccount] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [dba]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [dba]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [dba]
GO
ALTER ROLE [db_ddladmin] ADD MEMBER [dba]
GO
ALTER ROLE [db_backupoperator] ADD MEMBER [dba]
GO
ALTER ROLE [db_datareader] ADD MEMBER [dba]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [dba]
GO
ALTER ROLE [db_denydatareader] ADD MEMBER [dba]
GO
ALTER ROLE [db_denydatawriter] ADD MEMBER [dba]
GO
/****** Object:  Table [dbo].[Rezyserzy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rezyserzy](
	[idR] [int] NOT NULL,
	[imieR] [nvarchar](30) NOT NULL,
	[nazwiskoR] [nvarchar](60) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idR] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Aktorzy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Aktorzy](
	[idA] [int] NOT NULL,
	[imieA] [nvarchar](30) NOT NULL,
	[nazwiskoA] [nvarchar](60) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idA] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Gatunki]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Gatunki](
	[nazwaG] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nazwaG] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Filmy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Filmy](
	[idF] [int] NOT NULL,
	[nazwaF] [nvarchar](50) NOT NULL,
	[cenaF] [real] NOT NULL,
	[rokPowstaniaF] [int] NOT NULL,
	[opisF] [nvarchar](max) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idF] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Filmy_Rezyserzy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Filmy_Rezyserzy](
	[idF] [int] NULL,
	[idR] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Filmy_Aktorzy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Filmy_Aktorzy](
	[idF] [int] NULL,
	[idA] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Filmy_Gatunki]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Filmy_Gatunki](
	[idF] [int] NULL,
	[nazwaG] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[WidokFilm]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[WidokFilm] AS
   SELECT f.nazwaF, f.cenaF, f.rokPowstaniaF, f.opisF, r.imieR, r.nazwiskoR, a.imieA, a.nazwiskoA, g.nazwaG
   FROM Filmy f JOIN Filmy_Rezyserzy rf ON (rf.idF = f.idF) JOIN Rezyserzy r ON (rf.idR = r.idR)
				JOIN Filmy_Aktorzy af ON (af.idF = f.idF) JOIN Aktorzy a ON (af.idA = a.idA)
				JOIN Filmy_Gatunki fg ON (fg.idF = f.idF) JOIN Gatunki g ON (fg.nazwaG = g.nazwaG)
GO
/****** Object:  Table [dbo].[Wojewodztwa]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Wojewodztwa](
	[nazwaW] [nvarchar](19) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nazwaW] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AdresyKorespondencyjne]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AdresyKorespondencyjne](
	[idAD] [int] NOT NULL,
	[miastoAD] [nvarchar](30) NOT NULL,
	[ulicaAD] [nvarchar](40) NULL,
	[numerDomuAD] [int] NOT NULL,
	[numerMieszkaniaAD] [int] NOT NULL,
	[nazwaW] [nvarchar](19) NULL,
PRIMARY KEY CLUSTERED 
(
	[idAD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[WidokAdres]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[WidokAdres] AS
SELECT a.idAD, w.nazwaW, a.miastoAD, a.ulicaAD, a.numerDomuAD, a.numerMieszkaniaAD
FROM AdresyKorespondencyjne a JOIN Wojewodztwa w ON (w.nazwaW= a.nazwaW);
GO
/****** Object:  Table [dbo].[Pracownicy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Pracownicy](
	[idUP] [int] NOT NULL,
	[zarobkiP] [real] NOT NULL,
	[dataZatrudnieniaP] [date] NOT NULL,
	[idU] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idUP] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Uzytkownicy]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Uzytkownicy](
	[idU] [int] NOT NULL,
	[nazwaU] [nvarchar](50) NOT NULL,
	[hasloU] [int] NOT NULL,
	[imieU] [nvarchar](30) NOT NULL,
	[nazwiskoU] [nvarchar](60) NOT NULL,
	[idAD] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[nazwaU] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Wypozyczenia]    Script Date: 13.01.2018 18:09:36 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Wypozyczenia](
	[idW] [int] NOT NULL,
	[cenaW] [real] NOT NULL,
	[dataWypozyczeniaW] [date] NOT NULL,
	[dataZwrotuW] [date] NOT NULL,
	[idU] [int] NULL,
	[idF] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idW] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AdresyKorespondencyjne]  WITH CHECK ADD FOREIGN KEY([nazwaW])
REFERENCES [dbo].[Wojewodztwa] ([nazwaW])
GO
ALTER TABLE [dbo].[Filmy_Aktorzy]  WITH CHECK ADD FOREIGN KEY([idA])
REFERENCES [dbo].[Aktorzy] ([idA])
GO
ALTER TABLE [dbo].[Filmy_Aktorzy]  WITH CHECK ADD FOREIGN KEY([idF])
REFERENCES [dbo].[Filmy] ([idF])
GO
ALTER TABLE [dbo].[Filmy_Gatunki]  WITH CHECK ADD FOREIGN KEY([nazwaG])
REFERENCES [dbo].[Gatunki] ([nazwaG])
GO
ALTER TABLE [dbo].[Filmy_Gatunki]  WITH CHECK ADD FOREIGN KEY([idF])
REFERENCES [dbo].[Filmy] ([idF])
GO
ALTER TABLE [dbo].[Filmy_Rezyserzy]  WITH CHECK ADD FOREIGN KEY([idF])
REFERENCES [dbo].[Filmy] ([idF])
GO
ALTER TABLE [dbo].[Filmy_Rezyserzy]  WITH CHECK ADD FOREIGN KEY([idR])
REFERENCES [dbo].[Rezyserzy] ([idR])
GO
ALTER TABLE [dbo].[Pracownicy]  WITH CHECK ADD FOREIGN KEY([idU])
REFERENCES [dbo].[Uzytkownicy] ([idU])
GO
ALTER TABLE [dbo].[Uzytkownicy]  WITH CHECK ADD FOREIGN KEY([idAD])
REFERENCES [dbo].[AdresyKorespondencyjne] ([idAD])
GO
ALTER TABLE [dbo].[Wypozyczenia]  WITH CHECK ADD FOREIGN KEY([idF])
REFERENCES [dbo].[Filmy] ([idF])
GO
ALTER TABLE [dbo].[Wypozyczenia]  WITH CHECK ADD FOREIGN KEY([idU])
REFERENCES [dbo].[Uzytkownicy] ([idU])
GO
