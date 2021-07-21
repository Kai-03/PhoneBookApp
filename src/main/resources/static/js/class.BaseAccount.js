// Class BaseAccount: containing all account data
class BaseAccount {
  constructor(username="kai03", password="", first_name="", last_name="") {
    var data = {
      username: username,
      password: password,
      first_name: first_name,
      last_name: last_name
    }
    this.data = data;
  }

  clear() {
    var data = {
      username: "",
      password: "",
      first_name: "",
      last_name: ""
    }
    return data;
  }

}