1. MVVM Basic Structure
2. Consume - Employee List API and Based on Employee ID, Consume Comments API

Let's Understand Structure Of Project
* Adapter [ Recycleview Adapter ]
* Api [ Interface Of API methoda ( Only Methods , No Defination )  ]
* Common [ Network Check, Common Dialog Box Design ]
* Model [ API Related Getter , Setter - Data Classes ]
* Repository [ Here consume API and Take Response ]
* UI [ Activity and Fragment ]
* Util [ Common Error Message gnalder, Retrofit Instance Create, ApiThreeState Syntax ( Loading, Success, Error  ) ]
* ViewModel [ VM and VMFactory Of All API, Total 2 files of each API, VM --> LiveData, MutableLiveData , and In class Parameter Take Repository]
<img width="372" height="650" alt="image" src="https://github.com/user-attachments/assets/a5770410-8d71-4a88-a655-478141ba5b98" />

