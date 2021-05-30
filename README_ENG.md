<h1>AirLine Traffic Simulator</h1>
<img align="left"src="https://github.com/andreafoschi00/OOP20-alt-sim/blob/master/src/main/resources/images/logos/logo.png?raw=true" alt="logo">
<p align="right">
    <a href="https://github.com/andreafoschi00/OOP20-alt-sim"><strong>Go to project main page »</strong></a>
</p>
<br />
<p>
    AirLine Traffic Simulator is a videogame inspired by <a href="https://www.youtube.com/watch?v=KTH084KeFBc"><strong>Flight Control</strong></a>
    for AirLine traffic management. User goal is to <strong>land as many airplanes as possible</strong> without any collisions (cause game ending). 
    Airplanes spawn into the map progressively. <strong>User is in charge of airplanes movement</strong>, mouse drawing their routes. Game difficulty 
    increases with increasing number of airplanes landed    
  </p>

<h2>Mandatory features</h2>
 <ul>
    <li>Correct airplane-route mouse drawing</li>
    <li>Implementation of A.I. algorithm to move airplanes when user is not drawing (once <br />
        the airplane is in the map, it can't get out)</li>
    <li>Efficient airplane management when entering the map and landing on airstrip</li>
    <li>File management for names and scores</li>
    <li>Increasing difficulty throughout the game</li>
</ul>

<h2>Optional features</h2>
 <ul>
    <li>Creation and addition of dynamic maps (i.e. dynamic animations, objects causing explosion when flown over)</li>
    <li>Game sound implementation</li>
    <li>New airplanes with different speed</li>
    <li>Wind management: causing increasing or decreasing airplane speed</li>
</ul>

<h2>Challenges</h2>
 <ul>
    <li>Animations fluency (route drawing, airplanes movement and landing, collisions)</li>
    <li>MVC Pattern correct implementation</li>
    <li>Increasing difficulty throughout the game</li>
</ul> 

<h2>Delegation of tasks</h2>
 <ul>
    <li>
        <strong>Rodilosso</strong>
            <ul>
                <li>Airplane movements</li>
                <li>Airplane A.I. route implementation</li>
                <li>Animations</li>
            </ul> 
    </li>
    <li>
        <strong>Severi</strong>
            <ul>
                <li>UI and UX</li>
                <li>Airplane indicators when entering the map</li>
                <li>File management (saving, loading, displaying)</li>
            </ul> 
    </li>
    <li>
        <strong>Foschi</strong>
            <ul>
                <li>Landing Airstrip and Airplane landing</li>
                <li>Points deployment</li>
            </ul> 
    </li>
    <li>
        <strong>Atanasov</strong>
            <ul>
                <li>Collisions between airplanes</li>
                <li>Pause Menu Threads Management throughout the game</li>
            </ul> 
    </li>
</ul>
