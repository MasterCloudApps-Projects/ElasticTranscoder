#!/usr/bin/env sh
find '/usr/share/nginx/html' -name '*.js' -exec sed -i -e 's,BACKEND_API_URL,'"$BACKEND_API_URL"',g' {} \;
find '/usr/share/nginx/html' -name '*.js' -exec sed -i -e 's,OKTA_CLIENT_ID,'"$OKTA_CLIENT_ID"',g' {} \;
find '/usr/share/nginx/html' -name '*.js' -exec sed -i -e 's,OKTA_ISSUER,'"$OKTA_ISSUER"',g' {} \;
exec "$@"
