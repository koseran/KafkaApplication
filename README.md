Περιγραφή εργασίας.
1.	Φτιάξτε ένα απλό application(producer) (κατά προτίμηση σε java) το οποίο θα συνδέεται στον kafka broker και θα παράγει μηνύματα στο topic: task.events (σε json μορφή)
Τα events που θα παράγετε θα πρέπει να έχουν την εξής δομή
Task
String taskId
String studentId
String subject
String dateOfSubmission

Στο topic θα πρέπει να υπάρχουν τουλάχιστον 20 μηνύματα με 4 διαφορετικά θέματα εργασίας.

2.	Φτιάξτε ένα απλό application(consumer) (κατά προτίμηση σε java) το οποίο θα συνδέεται στον kafka broker και θα διαβάζει τα μηνύματα που έχουν παραχθεί : task.events και θα τα τυπώνει στην κονσόλα.
3.	Φτιάξτε ένα δεύτερο consumer ο οποίος θα διαβάζει τα μηνύματα από το topic task.events και ανάλογα με το subject, θα τα παράγει σε διαφορετικό topic ανά subject.
