from flask import Flask, request
from lxml import etree
from fault import build_fault, SoapFault
from response import build_response
from requests import namespaces
import ops
import datatypes
import traceback

app = Flask(__name__)

@app.route('/inventoryService/', methods=['POST'])
@app.route('/inventoryService', methods=['POST'])
def handleRequest():
   try:
      soap_in = etree.fromstring(request.data)
      op_element = soap_in.xpath("//soapenv:Body/*",
            namespaces=namespaces)[0]
      op_name = op_element.tag.split("}")[1]
   except:
      traceback.print_exc()
      return build_fault(SoapFault("Malformed request XML", SoapFault.BAD_XML))

   try:
      op = getattr(ops, op_name)
   except:
      traceback.print_exc()
      return build_fault(SoapFault("Unkown operation: %s" % op_element.tag,
         SoapFault.UNKNOWN_OP))

   try:
      data = getattr(datatypes, op_name)(op_element)
   except:
      traceback.print_exc()
      return build_fault(SoapFault("Error parsing request data"))

   try:
      template, response_obj = op(data)
      return build_response(template, response_obj)
   except SoapFault as fault:
      return build_fault(fault)
   except:
      traceback.print_exc()
      return build_fault(SoapFault("Server error"))

if __name__ == "__main__":
   app.run(host='0.0.0.0', port=8080)
