<h1>C-AirLine Traffic Simulator</h1>
<img align="left"src="https://github.com/andreafoschi00/OOP20-alt-sim/blob/master/src/main/resources/images/logos/logo.png?raw=true" alt="logo">
<p align="right">
    <a href="https://github.com/andreafoschi00/OOP20-alt-sim/blob/master/README_ENG.md"><strong>English Version »</strong></a>
</p>
<br />
<p>
    AirLine Traffic Simulator è un videogioco che si ispira a <a href="https://www.youtube.com/watch?v=KTH084KeFBc"><strong>Flight Control</strong></a>
    e ha come obiettivo la gestione del traffico aereo. L'obiettivo del giocatore è l'<strong>atterraggio del maggior numero possibile di aerei</strong>, i quali compariranno 
    progressivamente sulla mappa, evitando di farli collidere l'uno con l'altro (causando la fine del gioco). <strong>Il giocatore gestirà personalmente la direzione 
    di ogni velivolo</strong>, disegnandone il percorso che dovrà seguire ciascun aereo, con il mouse. La difficoltà di gioco aumenterà con l’aumentare del numero di 
    aerei che saranno fatti atterrare    
  </p>

<h2>Funzionalità necessarie</h2>
 <ul>
    <li>Disegnare correttamente con il mouse il percorso che l’utente vuol far seguire al velivolo selezionato</li>
    <li>Implementare un’intelligenza artificiale, che muoverà gli aerei quando non avranno una destinazione <br />
        scelta dall’utente (l’aereo una volta entrato nella mappa non potrà uscire, ma dovrà continuare a <br />
        volare all’interno di essa)</li>
    <li>Realizzare una gestione efficiente degli aerei in entrata sulla mappa e il relativo atterraggio quando <br />
        raggiungeranno la pista di atterraggio</li>
    <li>Salvataggio degli score dei vari utenti in un file apposito, che verrà reso disponibile ad ogni avvio</li>
    <li>Gestione della difficoltà crescente durante la partita</li>
</ul>

<h2>Funzionalità opzionali</h2>
 <ul>
    <li>Creazione e aggiunta di mappe dinamiche (es: implementazione di oggetti che causeranno la<br />
        distruzione dell’aereo se sorvolati, animazioni dinamiche nella mappa)</li>
    <li>Implementazione suoni di gioco</li>
    <li>Aerei speciali, con velocità diverse</li>
    <li>Gestione del vento: questo causerà una maggiore o minore velocità degli aerei durante la partita</li>
</ul>

<h2>Challenge Principali</h2>
 <ul>
    <li>“Fluidità" delle animazioni (tracciamento della rotta, movimento degli aerei, atterraggio degli aerei,<br />
        collisione tra gli aerei)</li>
    <li>Corretta implementazione del pattern MVC</li>
    <li>Gestione della difficoltà (crescente durante la partita)</li>
</ul> 

<h2>Suddivisione</h2>
 <ul>
    <li>
        <strong>Rodilosso</strong>
            <ul>
                <li>Movimento degli aerei (disegno della rotta)</li>
                <li>Implementazione I.A Aerei</li>
                <li>Animazione dei velivoli</li>
            </ul> 
    </li>
    <li>
        <strong>Severi</strong>
            <ul>
                <li>Ambiente di gioco</li>
                <li>Gestione degli aerei in entrata nella mappa, con inserimento indicatori</li>
            </ul> 
    </li>
    <li>
        <strong>Foschi</strong>
            <ul>
                <li>Piste di atterraggio</li>
                <li>Atterraggio degli aerei e punteggio</li>
            </ul> 
    </li>
    <li>
        <strong>Atanasov</strong>
            <ul>
                <li>Gestione dei vari aerei in partita</li>
                <li>Gestione collisione degli aerei</li>
                <li>Implementazione delle fasi di: inizio, pausa e fine partita</li>
            </ul> 
    </li>
</ul>
