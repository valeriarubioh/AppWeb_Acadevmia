// Create a function to parse the query string and return an object of query parameters
function parseQueryString(queryString) {
  const params = {};
  const pairs = (
    queryString[0] === "?" ? queryString.substr(1) : queryString
  ).split("&");

  for (const pair of pairs) {
    const [key, value] = pair.split("=");
    params[decodeURIComponent(key)] = decodeURIComponent(value || "");
  }

  return params;
}
