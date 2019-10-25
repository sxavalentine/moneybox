## Part A - Fix current bugs

### Bug 1 - Layout does not look as expected

For this part I just added or modified some constraints and added some margins.
For the TextInputLayout til_email I also changed the layout width from "0dp" to "match_parent"

![](/images/correct_layout.png)

### Bug 2 - Validation is incorrect

For this part, I made sure that the red errors would disappear once the wrong input is been corrected, by adding
-til_email.error = null
-til_password.error = null
-til_name.error = null

Also, since the field til_email is optional, I added an if condition that first check if the inputText text is not empty. 
If it's not empty, it proceeds checking that the input matches the regex (I also corrected the field, before it was checking if the password was matching NAME_REGEX).
if it matches the regex and the email and password were correct, the method allValids will return true, otherwise false and the name input field will be highlighted in red.

### Bug 3 - Animation is looping incorrectly

For this part, in the xml activity_login, I added app:lottie_loop="true" to the LottieAnimationView to make it loop. 
Then, in the LoginActivity class, I added an AnimatorListener to the LottieAnimationView, overriding the onAnimationRepeat method, so that when it starts again, it will go 
from frame 131 to 158.

## Part B - Add 2 new screens

### Screen 2 - User accounts screen

After getting the JSON response with the data to be used to build this view I created the corrispective Java classes (UserAccountJSON, Plan, Product), leaving out some unnecessary 
fields (such as InvestorAccount and Personalisation).
I then created the xml layout file "user_accounts" for the general user infos and a second xml layout "plan_view" to be used for each Plan object present in the JSON response.
According to the index of the Plan in the List<Plan> that I retrieve from the JSON, I gave its layout a different background color: green if it's odd, orange if it's even.
When I start the UserAccountActivity, I retrieve from Intent the JSON response of the login request, the eventual name in the login form and the BearerToken value obtained before.
This one, despite not being used directly in this activity, will be needed in the third screen, when I have to pass it for the OneOffPayment request.
If the intent doesn't contain a value for name, I set the visibility of the TextView "greeting" to GONE, so that is invisible and won't keep occupying space in the layout.
If present, instead, I set the greeting TextView text to "Hello " + {name} + " !".
Each button of the "plan_view" views, will be equipped with a Listener, so that when its pressed will be starting the OneOffPaymentActivity.
To start that, i will pass in the intent the BearerToken, the name (just to pass it back to UserAccountActivity when leaving) and the JSON of the Plan object that will be used to 
get all the parameters necessary to build the view.
When starting the OneOffPaymentActivity, the UserAccountActivity will be terminated.

### Screen 3 - Individual account screen
I first generated the "individual_account" xml layout file. Compared to the view number 3 in your example, mine has a "BACK" button in addition.
This is because I didn't want to start the OneOffPaymentActivity without terminating the UserAccountActivity. That way the user could come back to UserAccountActivity just 
by pressing the back arrow of the phone navigation bar.
Unfortunately this solution had a drawback: after completing a OneOffPayment with the "ADD 10£" button, we would have got a second OneOffPaymentActivity with the updated Moneybox
value, and the old UserAccountActivity from which we came from (in second position in the activity stack), still with the Moneybox field not updated. I didn't want the user to see
a non updated screen.
So i thought to make UserAccountActivity start the OneOffPaymentActivity through startActivityForResult, and by overloading the onActivityResult method I could came back and simply 
update the modified field. But I wasn't happy with this solution either, because this way the value Moneybox of the account would have been updated, but not the json private field 
of UserAccountActivity (which I use to build the Plan object that I pass to OneOffPaymentActivity. This way, should the user decide to perform a second OneOffPayment right after 
the first one, it would have see a non-updated single account view).
In the end, the solution of a back button was the easiest and fastest. Whenever we move from screen 2 to screen 3, screen 2 gets terminated, and when we move back from screen 3 to 2,
screen 2 gets created from scratch once again from an updated JSON.

### Library used:
Http3 for http requests
Gson for JSON manipulation

### Other tasks performed:
I set all activities with a portrait screen orientation, the landscape seemed not to fit the app style.