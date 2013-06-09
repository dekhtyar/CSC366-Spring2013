from flask import Flask, request
from lxml import etree
from fault import build_fault, SoapFault
from response import build_response
import ops
import datatypes

app = Flask(__name__)
namespaces = {'soapenv': "http://schemas.xmlsoap.org/soap/envelope/",
              'v4': "http://v4.core.coexprivate.api.shopatron.com"}


def recursive_dict(element):
   return element.tag.split("}")[1], \
         dict(map(recursive_dict, element)) or element.text

#def recursive_dict(element):
#   if (len(element) == 1):
#      return element.tag.split("}")[1], \
#            dict(map(recursive_dict, element)) or element.text
#   else:
#      ret = []
#      for child in element:
#         ret.append(map(recursive_dict, child) or child.text)
#
#      return element.tag.split("}")[1], ret


@app.route('/inventoryService/', methods=['POST'])
@app.route('/inventoryService', methods=['POST'])
def handleRequest():
   try:
      soap_in = etree.fromstring(request.data)
      op_element = soap_in.xpath("//soapenv:Body/*",
            namespaces=namespaces)[0]
      op_name, op_dict = recursive_dict(op_element)
      print(op_dict)
   except Exception as e:
#      print(e)
      return build_fault(SoapFault("Malformed request XML", SoapFault.BAD_XML))

   try:
      op = getattr(ops, op_name)
      data = getattr(datatypes, op_name)(op_dict)
   except:
      return build_fault(SoapFault("Unkown operation: %s" % op_element.tag,
         SoapFault.UNKNOWN_OP))

   try:
      template, response_obj = op(data)
      return build_response(template, response_obj)
   except SoapFault as fault:
      return build_fault(fault)
   except Exception as e:
#      print(e)
      return build_fault(SoapFault("Server error"))

if __name__ == "__main__":
   app.run(host='0.0.0.0', port=8080)
