### Web UI

In order to make use of the web UI, you have to support [CORS][1].
Alternatively you can make your web server serve up the web UI and all it's
assets, however that will be much more complicated once the UI gets more
development.

For those looking for the quick and easy way, make all HTTP responses add the
header:

    Access-Control-Allow-Origin: *

The article provides a better explanation, but it's really long so I will
summarize. When you load a page, that page is not allowed to make any requests
to any domain other than the one you loaded it from. For example, the web UI to
your service. This is a browser feature for in place for security reasons
([XSS][2]).

However, sometimes you want to share resources among different websites, so
there is a standard called Cross Origin Resource Sharing, supported by most
modern browsers. The way this works is a server can specify that a certain
other origin is allowed to access it's resources, via the
`Access-Control-Allow-Origin` HTTP header.

You can allow other domains to access resources selectively, if you reply with
the same value as in the `Origin` header that will be attached by the browser
when JavaScript initiated the request. Alternatively, you can reply with the
value "`*`" to indicate anyone can access the resource at that location. This
is what [we do][3].

Sometimes CORS does request pre-flighting, which is sending an `OPTIONS`
request to the endpoint the page is trying to access. Our requests are designed
to _not_ trigger pre-flighting for simplicity. In particular, this means that
the SOAP message will be sent as `text/plain`, and the `SOAPAction` header
isn't included. If you don't care about those, you are solid. If you do, either
stop, or read the link at the top of this document yourself.

[1]: https://developer.mozilla.org/en-US/docs/HTTP/Access_control_CORS
[2]: https://en.wikipedia.org/wiki/Cross-site_scripting
[3]: https://github.com/dekhtyar/CSC366-Spring2013/blob/aperture-science/src/apersci/service/main.go#L58
