import axios from "axios"
import { useState } from "react"
import { Link } from "react-router-dom"

const AddUser=()=>{

    const [name, setName] = useState("")
    const [email, setEmail] = useState("")
    const [phone, setPhone] = useState("")
    const [company, setCompany] = useState("")

    const [success, setSuccess] = useState("")

     const postApi = "https://jsonplaceholder.typicode.com/users"

      const submitForm = async (e) => {
        e.preventDefault()

        try {
           const response= await axios.post(postApi, {
                "name": name,
                "email": email,
                "phone": phone,
                "company": {
                    "name": company
                }
            })
            console.log(response)
            console.log(response.data)
            setSuccess("User Added Successfully!")
        }
        catch (err) {
            console.log(err)
        }
    }
    return(
         <div className="container-fluid p-4">
             <Link to={'/users'}> Back to Users</Link>
            <div className="row mt-4 mb-4">
                <div className="col-sm-2"></div>
                <div className="col-md-8">
                    {
                        success === undefined ? "" :
                            <div className="alert alert-success mt-4">
                                {success}
                            </div>
                    }
                    <div className="card mb-2">
                        <div className="card-header">
                            Add New User
                        </div>
                        <div className="card-body">
                            <form onSubmit={(e) => submitForm(e)}>

                                {/* name */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Name</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="text"
                                            required= "required"
                                            onChange={(e) => setName(e.target.value)} />
                                    </div>
                                </div>

                                {/* email */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Email</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="email"
                                            required= "required"
                                            onChange={(e) => setEmail(e.target.value)} />
                                    </div>
                                </div>

                                {/* phone number */}
                                <div className="row mt-2">
                                    <div className="col-md-3">
                                        <label>Enter Phone Number</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="number"
                                            required= "required"
                                            onChange={(e) => setPhone(e.target.value)} />
                                    </div>
                                </div>

                                {/* company name */}
                                <div className="row mt-2 mb-2">
                                    <div className="col-md-3">
                                        <label>Enter Company Name</label>
                                    </div>
                                    <div className="col-md-9">
                                        <input className="form-control"
                                            type="text"
                                            required= "required"
                                            onChange={(e) => setCompany(e.target.value)} />
                                    </div>
                                </div>

                                <button className="btn btn-primary" type="submit">
                                    SUBMIT
                                </button>

                            </form>

                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )

}
export default AddUser