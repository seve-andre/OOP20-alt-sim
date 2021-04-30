C - alt-sim -
Andrea Foschi, Daniel Rodilosso, Atanasov Atanas Todorov, Andrea Severi-Airline Traffic Simulator
Il gruppo si pone come obiettivo quello di realizzare un videogioco sulla gestione del traffico aereo, ispirandosi al gioco mobile “Flight Control” (https://www.youtube.com/watch?v=KTH084KeFBc).
L’obiettivo del giocatore sarà quello di far atterrare il maggior numero di aerei che compariranno progressivamente sulla mappa, evitando di farli collidere tra di loro (causando la fine del gioco).
Il giocatore gestirà personalmente la direzione di ogni velivolo, disegnandone il percorso che dovrà seguire ciascun aereo, con il mouse.
La difficoltà di gioco aumenterà con l’aumentare del numero di aerei che saranno fatti atterrare.
FUNZIONALITÀ NECESSARIE:
•	Disegnare correttamente con il mouse il percorso che l’utente vuol far seguire al velivolo selezionato.

•	Implementare un’intelligenza artificiale, che muoverà gli aerei quando non avranno una destinazione scelta dall’utente (l’aereo una volta entrato nella mappa non potrà uscire, ma dovrà continuare a volare all’interno di essa).

•	Realizzare una gestione efficiente degli aerei in entrata sulla mappa e il relativo atterraggio quando raggiungeranno la pista di atterraggio.

•	Salvataggio degli score dei vari utenti in un file apposito, che verrà reso disponibile ad ogni avvio.

•	Gestione della difficoltà crescente durante la partita.

FUNZIONALITÀ OPZIONALI:
•	Creazione e aggiunta di mappe dinamiche (es: implementazione di oggetti che causeranno la distruzione dell’aereo se sorvolati, animazioni dinamiche nella mappa).

•	Implementazione suoni di gioco.

•	Aerei speciali, con velocità diverse.

•	Gestione del vento: questo causerà una maggiore o minore velocità degli aerei durante la partita.


CHALLENGE PRINCIPALI:
•	“Fluidità" delle animazioni (tracciamento della rotta, movimento degli aerei, atterraggio degli aerei, collisione tra gli aerei).
•	Corretta implementazione del pattern MVC.
•	Gestione della difficoltà (crescente durante la partita).
 
SUDDIVISIONE:
•	RODILOSSO:
o	Movimento degli aerei (disegno della rotta).
o	Implementazione I.A Aerei.
o	Animazione dei velivoli.

•	SEVERI:
o	Ambiente di gioco.
o	Gestione degli aerei in entrata nella mappa, con inserimento indicatori.

•	FOSCHI:
o	Piste di atterraggio.
o	Atterraggio degli aerei e punteggio.
o	Menù iniziale.

•	ATANASOV:
o	Gestione dei vari aerei in partita.
o	Gestione collisione degli aerei.
o	Implementazione delle fasi di: inizio, pausa e fine partita.


