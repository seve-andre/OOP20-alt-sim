# AirLine Traffic Simulator
<img align="left" src="https://github.com/seve-andre/OOP20-alt-sim/blob/master/src/main/resources/images/logos/logo.png?raw=true" alt="logo">
<p align="right">
    <a href="https://github.com/seve-andre/OOP20-alt-sim"><strong>Go to main page »</strong></a>
</p>
<br />

AirLine Traffic Simulator è un videogioco che si ispira a [Flight Control](https://www.youtube.com/watch?v=KTH084KeFBc) e ha come obiettivo la gestione del traffico aereo. L'obiettivo del giocatore è l'**atterraggio del maggior numero possibile di aerei**, i quali compariranno progressivamente sulla mappa, evitando di farli collidere l'uno con l'altro (causando la fine del gioco). **Il giocatore gestirà personalmente la direzione 
di ogni velivolo**, disegnandone il percorso che dovrà seguire ciascun aereo, con il mouse. La difficoltà di gioco aumenterà con l’aumentare del numero di aerei che saranno fatti atterrare.  

## Funzionalità necessarie
- Disegnare correttamente con il mouse il percorso che l’utente vuol far seguire al velivolo selezionato
- Implementare un’intelligenza artificiale, che muoverà gli aerei quando non avranno una destinazione scelta dall’utente (l’aereo una volta entrato nella mappa non potrà uscire, ma dovrà continuare a volare all’interno di essa)
- Realizzare una gestione efficiente degli aerei in entrata sulla mappa e il relativo atterraggio quando raggiungeranno la pista di atterraggio
- Salvataggio degli score dei vari utenti in un file apposito, che verrà reso disponibile ad ogni avvio
- Gestione della difficoltà crescente durante la partita

## Funzionalità opzionali
- Creazione e aggiunta di mappe dinamiche (es: implementazione di oggetti che causeranno la distruzione dell’aereo se sorvolati, animazioni dinamiche nella mappa)
- Implementazione suoni di gioco
- Aerei speciali, con velocità diverse
- Gestione del vento: questo causerà una maggiore o minore velocità degli aerei durante la partita

### Challenge Principali
- “Fluidità" delle animazioni (tracciamento della rotta, movimento degli aerei, atterraggio degli aerei, collisione tra gli aerei)
- Corretta implementazione del pattern MVC
- Gestione della difficoltà (crescente durante la partita)

### Suddivisione
- **Rodilosso**
    - Movimento degli aerei (disegno della rotta)
    - Implementazione I.A aerei
    - Animazione dei velivoli
    - Gestione dei vari aerei in partita
- **Severi**
    - Ambiente di gioco
    - Gestione degli aerei in entrata nella mappa, con inserimento indicatori
    - Implementazione delle fasi di: inizio, pausa e fine partita
    - Gestione file punteggi
- **Foschi**
    - Piste di atterraggio
    - Atterraggio degli aerei e punteggio
    - Gestione collisione degli aerei
