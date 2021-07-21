// Class BaseAccount: containing all account data
class BaseData {
  constructor(first_name="", last_name="", phone="", email="", user_id="") {
	var data = {
		first_name: first_name,
		last_name: last_name,
		phone: phone,
		email: email,
		user_id: user_id
	}
    this.data = data;
  }

  clear() {
	var data = {
		first_name: "",
		last_name: "",
		phone: "",
		email: "",
		user_id: ""
	}
    return data;
  }

}