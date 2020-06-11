```
 ________  ________  ___      ___ ___  ________  ________  _________  ________
|\   ____\|\   __  \|\  \    /  /|\  \|\   ___ \|\   __  \|\___   ___\\   __  \
\ \  \___|\ \  \|\  \ \  \  /  / | \  \ \  \_|\ \ \  \|\  \|___ \  \_\ \  \|\  \
 \ \  \    \ \  \\\  \ \  \/  / / \ \  \ \  \ \\ \ \   __  \   \ \  \ \ \   __  \
  \ \  \____\ \  \\\  \ \    / /   \ \  \ \  \_\\ \ \  \ \  \   \ \  \ \ \  \ \  \
   \ \_______\ \_______\ \__/ /     \ \__\ \_______\ \__\ \__\   \ \__\ \ \__\ \__\
    \|_______|\|_______|\|__|/       \|__|\|_______|\|__|\|__|    \|__|  \|__|\|__|

```





[toc]

# Graph Descriptif du TODO

```mermaid
	graph TB
		A((MainActivity)) -- On Data Btn Click --> B(DataActivity ++API)
		A -- On Graph Btn Click --> C(GraphActivity ++API)
		A -- On Mystery Click --> E(Mistery Page ++API)
		A -- On logo Click --> F(GAME with API ?)
		B -- on a country line click --> D(CalendarActivity ++API)
		D -- On a date click, must actualize informations --> D
```



Ce graphe présente l'ensemble des activités et des Listeners à implémenter et indique les activités ayant besoin de la data base.



> <u>/!\</u> Toutes les strings des textview doivent être dans les *resources values*.



# TODO



## MAIN ACTIVITY

- [ ] LAYOUT
- [ ] On DataBtn Click Listener
- [ ] On GraphBtn Click Listener
- [ ] On MisteryBtn Click Listener
- [ ] ***BONUS :*** on LogoBtn Listener



## DATA ACTIVITY

- [ ] Layout
- [ ] Recycler View Layout
- [ ] Recycler View Optimization
- [ ] On Click Listener for List Element (Country name sent to Calendar Activity)
- [ ] RETROFIT INTERFACE AND DATACLASSES



## GRAPH ACTIVITY

- [ ] Layout
- [ ] Recycler View Layout
- [ ] Recycler View Optimization
- [ ] Listener for each element which actualize graph data
- [ ] Graph which present data per day of in terms of : 
  - [ ] Confirmed Btn
  - [ ] DeathsBtn
  - [ ] RecoveryBtn
- [ ] RETROFIT INTERFACE AND DATACLASSES



## CALENDAR ACTIVITY

- [ ] LAYOUT
- [ ] Calendar View
- [ ] On Date Changed Listener
- [ ] RETROFIT INTERFACE AND DATACLASSES



## Mistery Activity

Ideas :

- Maybe pie graph and calendar gathered on the same activity
  - The graph shows confirmed cases, deaths and Recoveries in the same piegraph per day





## EASTEREGG ACTIVITY

- [ ] Layout
- [ ] GameAPI