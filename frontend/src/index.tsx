import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
// import { initKeycloak } from './Services/KeyCloakService';

// keycloak.init({
//   onLoad: 'login-required',
//   redirectUri: window.location.origin
// }).then(authenticated => {
//   if (authenticated) {
//     const root = ReactDOM.createRoot(
//       document.getElementById('root') as HTMLElement
//     );
//     root.render(
//       <React.StrictMode>
//         <App />
//       </React.StrictMode>
//     );
//   } else {
//     console.error("Not authenticated");
//   }
// }).catch(console.error);


// initKeycloak().then((keycloak) => {
//   const roles = keycloak.tokenParsed?.realm_access?.roles ?? [];


//     // 


//     console.log('index called');
//   // if (roles.includes('admin')) {
//   //   window.location.href = '/registration';
//   // } else {
//   //   window.location.href = '/home';
//   // }

//   // Optional: render app after role-based redirect
//   // ReactDOM.createRoot(document.getElementById('root')!).render(<App />);
// }).catch((err) => {
//   console.error("Keycloak init failed", err);
// });


    const root = ReactDOM.createRoot(
      document.getElementById('root') as HTMLElement
    );
    root.render(
      <App />
    );

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
