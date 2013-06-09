from flask import render_template, make_response 

def soap_op(operation):
   def wrapped_operation(*args, **kwargs):
      template = operation.__name__ + ".xml"
      return template, operation(*args, **kwargs)

   return wrapped_operation

def build_response(template, response_obj, code=200):
   response = make_response(render_template(template, response=response_obj), code)

   response.headers['Access-Control-Allow-Origin'] = 'http://db2.thepicard.org'
   response.headers['Content-Type'] = 'text/xml'

   return response
