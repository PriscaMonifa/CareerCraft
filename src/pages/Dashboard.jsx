import { useNavigate } from "react-router-dom"

const Dashboard = ()=>{
    const navigate= useNavigate()

    return (
         <div className='container-fluid p-4'>
      <h2>User Management Dashboard</h2>

      <div className="row mt-4">
        <div className="col-md-4"></div>
        <div className="col-sm-2">
          <button className="btn btn-primary"
          onClick={()=> navigate('/users')}>
            Go to User List
          </button>
        </div>
        <div className="col-sm-1"></div>
        <div className="col-sm-2">
          <button className="btn btn-primary me-2"
          onClick={()=> navigate('/add-user')}>
            Add new User
          </button>
        </div>
      </div>
      
    </div>
    )
}

export default Dashboard