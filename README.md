-------------Instalacja i konfigurowanie systemu-------------

Stworzona aplikacja desktopowa zawarta jest w pliku wykonywalnym Wypozyczalnia.jar.
Na jej otwarcie niezbędne będzie zainstalowanie jre (ang. Java Runtime Environment), 
do którego instalator załączony został w folderze z aplikacją pod nazwą JavaSetup8u151.exe. 
W folderze lib zawarte są biblioteki używane przez aplikację. Żeby utworzyć bazę danych 
niezbędne jest otwarcie dołączonego z aplikacją skryptu Wypozyczalnia.sql za pomocą 
programu Microsoft SQL Server Management Server 17 na porcie 1433. 

-------------Instrukcja użytkowania aplikacji-------------

Aplikacja stworzona została zarówno dla klientów wypożyczalni jak i dla sprzedawców. 
Po otwarciu aplikacji możliwy jest wybór menu klienta, menu sprzedawcy lub wyjście z aplikacji.

W menu klienta możliwe jest stworzenie konta przez podanie: nazwy konta, hasła, imienia, 
nazwiska, województwa, miasta, ulicy (nieobowiązkowa), numeru domu oraz numeru mieszkania (nieobowiązkowe). 
Wymaganie dotyczące hasła jest takie, że musi mieć od 8 do 16 znaków. Możliwe jest także zalogowanie 
przy pomocy podania nazwy konta oraz hasła, oraz powrót do menu głównego aplikacji.

Po zalogowaniu się na konto widać dane zalogowanego użytkownika oraz pierwszą stronę listy 
filmów (5 różnych filmów). Mamy możliwość zmiany hasła, adresu, a także nazwiska. Możemy 
usunąć konto (dezaktywować) oraz przejrzeć historię wypożyczeń dla naszego konta. W opcjach 
konta jest także przeglądanie listy filmów. Za pomocą guzików do zmiany stron zmieniają się 
nazwy filmów na 5 różnych guzikach. Możliwe jest wyszukanie filmów przez podanie nazwy filmu, 
gatunku filmu lub roku powstania filmu. Po wybraniu filmu przechodzimy na stronę wypożyczenia 
na której widzimy następujące dane wybranego filmu: nazwa filmu, gatunek, rok powstania, 
opis filmu, cena wypożyczenia oraz główni reżyserzy i aktorzy. W tym momencie możliwe jest 
przejście do płatności a następnie przejście do przelewu bankowego lub powrót do konta.

Przy wejściu do menu sprzedawcy otrzymujemy informację ostrzegającą, że nieautoryzowane wejście 
może być ukarane. Jest to informacja dla klientów, którzy próbowaliby zalogować się jako sprzedawca. 
W menu sprzedawcy mamy jedynie możliwość zalogowania się. Administrator bazy danych musi 
stworzyć konto pracownika. Po zalogowaniu się na konto widać dane zalogowanego pracownika. 
Możliwa jest zmiana hasła, nazwiska oraz adresu. Możliwe jest wyświetlenie wszystkich kont 
utworzonych w bazie danych, przejrzenie wypożyczeń oraz edycja filmów, a także wylogowanie się z konta.

Po wybraniu przeglądania wypożyczeń otrzymujemy stronę ze wszystkimi wypożyczeniami które 
możemy przeglądać przy pomocy guzików "Poprzednie wypożyczenie" oraz "Następne wypożyczenie". 
Wypożyczenia możemy wyszukać podając: ID wypożyczenia, nazwę filmu lub nazwę klienta. 

Po wybraniu edycji filmów otrzymujemy listę filmów, podobną jak w przypadku menu klienta, 
pola które zawierać będą informacje o: ID filmu, nazwie filmu, gatunku filmu, roku powstania, 
opisie filmu, aktualnej cenie oraz najważniejszych reżyserach i aktorach. Mamy także opcje 
wyszukania, edycji, utworzenia oraz usunięcia filmu. Przeglądanie filmów polega na zmianie 
stron przy pomocy przycisków "Poprzednia strona" oraz "Następna strona". Po wybraniu filmu, 
pola wypełniają się informacjami o wybranym filmie. Wyszukanie filmu (guzik "Znajdź") 
pobiera jedynie wpisaną nazwę filmu, gatunek (koniecznie jeden) oraz rok powstania filmu 
i zwraca wyniki na liście filmów. Edycja filmu wyszukuje podany film i przypisuje do niego 
zmienione dane (pola nie mogą być puste). Do usunięcia filmu (dezaktywacji) konieczne jest 
potwierdzenie, żeby film nie został usunięty przez przypadek.
