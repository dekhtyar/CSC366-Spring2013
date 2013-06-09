from response import build_response

class SoapFault(Exception):
   GENERIC = 500
   UNKNOWN_OP = 501
   BAD_XML = 400

   def __init__(self, err_str, err_type=GENERIC):
      self.err_str = err_str
      self.err_type = err_type

   def __str__(self):
      return self.err_str

def build_fault(fault):
   return build_response("fault.xml", fault, fault.err_type)
