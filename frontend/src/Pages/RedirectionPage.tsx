import './Common.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { initCheckSso } from '../Services/KeyCloakService';
import axios from 'axios';

const RedirectionPage: React.FC = () => {
  const navigate = useNavigate();
  
  const [user, setUser] = useState<any>(null);
  
const location = useLocation();
const queryParams = new URLSearchParams(location.search);
const isRegistration = queryParams.get("isRegistration") === "true";

  useEffect(() => {
        initCheckSso()
        .then(kc => {
            if (!kc.authenticated) {
                // no session → send back to login
                kc.login();
                return;
            }
            // we have a live session: kc.tokenParsed is populated
            setUser(kc.tokenParsed);
            
            const newUser = kc.tokenParsed
            const roles = newUser?.realm_access?.roles;
            console.log("Roles:", roles);
            console.log("Email:", newUser?.email);
            console.log("firstName:", newUser?.firstName);
            console.log("lastName:", newUser?.lastName);
            console.log("phone_number:", newUser?.phone_number);


            console.log("user detected");
            console.log(newUser);

            if(isRegistration){
                const url = 'http://localhost:8080/customers/register';
                
                const requestBody = {
                    firstName     : newUser?.firstName,
                    lastName      : newUser?.lastName,
                    phoneNumber   : newUser?.phone_number,
                    email         : newUser?.email
                };

                axios.post(url, requestBody)
                    .then(response => {
                        console.log('Response:', response.data);
                        })
                        .catch(error => {
                        console.error('Error:', error);
                    });
            }

            if(roles!=null && roles.includes('admin')){
                navigate("/adminPage");
            }else{
                navigate("/home");
            }
        })
        .catch(console.error);
    }, []);

    return <div>Loading…</div>;
}
export default RedirectionPage