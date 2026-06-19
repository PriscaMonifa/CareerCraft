import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const UserList=()=>{
    
const getApi="https://jsonplaceholder.typicode.com/users"
const [users, setUsers] = useState([]);

  useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get(getApi)

                setUsers(response.data)
                console.log(response.data)
            }
            catch (err) { 
                console.log(err)
            }
        }
        fetchUsers()
    }, [])

    const deleteUser= async (id)=>{
        const deleteApi= `https://jsonplaceholder.typicode.com/users/${id}`

        try{
            await axios.delete(deleteApi)

            const deletedList= users.filter(u=> 
                u.id !==id
            )
            setUsers(deletedList)
        }
        catch(err){err}

    }


    return(
         <div className="container-fluid p-4">
             <Link to={'/'}> Back to Dashboard</Link>
            <h2>User List</h2>

            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">S.no</th>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Company Name</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        users.map((user, index) => (
                            <tr key={index}>
                                <td>{index+1}</td>
                                <td>{user.name}</td>
                                <td>{user.email}</td>
                                <td>{user.phone}</td>
                                <td>{user.company.name}</td>
                                <td>
                                    <button className="btn btn-danger"
                                    onClick={()=> deleteUser(user.id)}>
                                        Delete User
                                    </button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    )

}
export default UserList