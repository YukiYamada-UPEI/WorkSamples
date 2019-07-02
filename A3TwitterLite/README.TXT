
PROJECT TITLE: TwitterLight
PURPOSE OF PROJECT: The purpose is to design and implement a mini version of Twitter, complete with graphical user interface (GUI).
VERSION or DATE: 2018/03/30
HOW TO START THIS PROJECT: The TestDriver class has a main method, so please run the TestDriver class first. It shows a AdminControlPanel.
AUTHORS: Yuki Yamada(138557) and Aleix Molla(146072)
USER INSTRUCTIONS:

<<Instructions of operations>>

[Add User]
Enter a user id you like, and click the group you want the new user to belong to (i.e. Root) from the left JTree. And press "Add User" buttton.

[Add Group]
Enter a group id you like, and click the group you want the new group to belong to (i.e. Root) from the left JTree. And press "Add Group" buttton.

[Open a User View]
Click a  user from the left JTree, and click "Open User View" button. 


<<features/weaknesses/known bugs>>

/Each user panel is updated by a timer that each panel has. Since we implements MVC architecture and a model (User) does not have a reference to a view (UserPanel), a panel updates itself by itself. 

/You can open a multiple user panels for the same user. Of course, they are synchronized. 

/The same id is not allowed even between users and groups as well. 

/You can only choose a group to which a user belong. (ie you cannot change a group of a user after you add a user because the change of the group involves the removal of a user. 

/When a group(folder) has no users or groups in it, its icon is the same as the normal user(file).

/Any JTextArea has a focus & unfocus feauture to easy write text, a known bug is if you write something and unfocus refocus, the text is going to dissapear.

/Positive words are "Good", "Great", "Excellent", "Easy", "Happy", "Lucky".

/You cannot see the followings posts that had posted before you follow him/her.

 