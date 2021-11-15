package com.gamecodeschool.en_leo.Model;

public class UserProfile {
        public String userAge;
        public String userEmail;
        public String userName;
        public String userFullName;
        public String userBloodGroup;
        public String userContact;

        public UserProfile(){
        }

        public UserProfile(String userAge, String userEmail, String userName, String userFullName, String userBloodGroup, String userContact) {
            this.userAge = userAge;
            this.userEmail = userEmail;
            this.userName = userName;
            this.userFullName = userFullName;
            this.userBloodGroup = userBloodGroup;
            this.userContact = userContact;
        }


        public String getUserAge() {
            return userAge;
        }

        public void setUserAge(String userAge) {
            this.userAge = userAge;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserFullName() { return userFullName; }

        public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

        public String getUserBloodGroup() { return userBloodGroup; }

        public void setUserBloodGroup(String userBloodGroup) { this.userBloodGroup = userBloodGroup; }

        public String getUserContact() { return userContact; }

        public void setUserContact(String userContact) { this.userContact = userContact; }
}

