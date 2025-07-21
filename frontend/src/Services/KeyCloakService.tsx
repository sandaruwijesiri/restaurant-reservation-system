const CONFIG = {
  url:      'http://localhost:8000',
  realm:    'restaurant-application',
  clientId: 'login-registration-client'
};

// your React app’s home page
const REDIRECT_HOME = window.location.origin + '/redirect';
// for silent‐check SSO iframe
const SILENT_CHECK  = window.location.origin + '/silent-check-sso.html';

let initialized: boolean = false;
export let kc: any;

export async function getKc() {
  if (!kc) {
    const { default: Keycloak } = await import('keycloak-js');
    kc = new Keycloak(CONFIG);
  }
  return kc;
}

/** Called by your “Log in” button */
export async function keycloakLogin() {
  const keycloak = await getKc();

  // ensure URLs are set up
  await initKeycloak(keycloak);

  // now build & navigate
  keycloak.login({ redirectUri: REDIRECT_HOME + '?isRegistration=false' });
}

/** Called by your “Register” button */
export async function keycloakRegistration() {
  const keycloak = await getKc();

  // 1) silently init so URL builders are ready
  await initKeycloak(keycloak);

  // 2) build the correct URL
  const registerUrl = await keycloak.createRegisterUrl({
    redirectUri: REDIRECT_HOME + '?isRegistration=true',
    // you can also pass scope or locale here if needed
  });

  // 3) redirect the browser
  window.location.href = registerUrl;
}

/** Called inside Home to restore session silently */
export async function initCheckSso() {
  const keycloak = await getKc();
  await keycloak.init({
    onLoad: 'check-sso',
    silentCheckSsoRedirectUri: SILENT_CHECK,
    // no need for redirectUri here
  });
  return keycloak;
}

async function initKeycloak(keycloak: { init: (arg0: { onLoad: string; silentCheckSsoRedirectUri: string; }) => void; }){
  if(!initialized){
    keycloak.init({ onLoad: 'check-sso', silentCheckSsoRedirectUri: SILENT_CHECK });
    initialized = true;
  }
}
