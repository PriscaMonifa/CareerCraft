import { Route, Routes } from "react-router-dom";
import PageNotFound from "./pages/PageNotFound";
import UserList from "./components/UserList";
import AddUser from "./components/AddUser";
import Dashboard from "./pages/Dashboard";

const App = ()=>{
 

 return ( 
  <div>
      <Routes>
        <Route path="/" element={<Dashboard />}></Route>
        <Route path="/users" element={<UserList />}></Route>
        <Route path="/add-user" element={<AddUser />}></Route>
        <Route path="*" element={<PageNotFound />}></Route>
      </Routes>
    </div>
 )

}

export default App; 
