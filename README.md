* MVVM Basic Structure
*  Consume - Employee List API and Based on Employee ID, Consume Comments API

Let's Understand Structure Of Project
1. Adapter [ Recycleview Adapter ]
2. Api [ Interface Of API methoda ( Only Methods , No Defination )  ]
3. Common [ Network Check, Common Dialog Box Design ]
4. Model [ API Related Getter , Setter - Data Classes ]
5. Repository [ Here consume API and Take Response ]
6. UI [ Activity and Fragment ]
7. Util [ Common Error Message gnalder, Retrofit Instance Create, ApiThreeState Syntax ( Loading, Success, Error  ) ]
8. ViewModel [ VM and VMFactory Of All API, Total 2 files of each API, VM --> LiveData, MutableLiveData , and In class Parameter Take Repository]
<img width="372" height="650" alt="image" src="https://github.com/user-attachments/assets/a5770410-8d71-4a88-a655-478141ba5b98" />

