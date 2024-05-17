#! /usr/bin/env python


import sys
sys.path.append('/usr/share/inkscape/extensions')
import inkex
import os
import subprocess
import tempfile
import shutil
import copy
import logging

class PNGPeronalExport(inkex.Effect):
    def __init__(self):
        logging.basicConfig(filename='c:\\logs\\ink_extension.log', level=logging.DEBUG, format='%(asctime)s - %(levelname)s - %(message)s')
        """ stdout_handler = logging.StreamHandler(stream=sys.stdout)"""
        """stdout_handler.setLevel(logging.DEBUG)"""
        """init the effetc library and get options from gui"""
        """inkscape -C -j -i layer11 -o soldato_frame3.png pedinaSoldatoSkyBase.svg"""
        logging.info("Extension function executed successfully.")
        inkex.Effect.__init__(self)
        self.arg_parser.add_argument("--path", action="store", type=str, dest="path", default="/", help="")
        self.arg_parser.add_argument('-f', '--filetype', action='store', type=str, dest='filetype', default='png', help='Exported file type')
        self.arg_parser.add_argument("--crop", action="store", type=bool, dest="crop", default=False)
        self.arg_parser.add_argument("--dpi", action="store", type=float, dest="dpi", default=90.0)
        self.arg_parser.add_argument("--layersName", action="store", type=str, dest="layersName", default="")
    
    def effect(self):


        list_layer = os.path.expanduser(self.options.layersName).split("|")
        logging.info("Extension function layersName."+self.options.layersName+" ")
        curfile = self.options.input_file[-1]
        logging.info("Extension function curfile."+self.options.input_file+" ")
        svg_layers = self.document.xpath('//svg:g[@inkscape:groupmode="layer"]', namespaces=inkex.NSS)
        layers = []
        tempDir=tempfile.NamedTemporaryFile()
        dest=tempDir.name+".svg"
        doc = copy.deepcopy(self.document)
        doc.write(dest)
        counter = 1
        tempDir.close();
        for layer in svg_layers:
          layer_id = layer.attrib["id"]
          for exportId in list_layer:
                if layer_id == exportId :  
                    logging.info("Extension function cehck."+layer_id+" ")
                    logging.info("Extension function tempfile."+dest+" ")
                    esporta_livelli_in_png(self,layer_id,dest)
                    counter = counter +1
                    logging.info("Extension function counter."+str(counter)+" ")
                    
                
def esporta_livelli_in_png(self,layer_id,dest):
        output_path = os.path.expanduser(self.options.path)+ "\\"
        filetype = os.path.expanduser(self.options.filetype)
        area_param = "-D"
        if self.options.crop:
            area_param = "-C"
        
        # Esegui Inkscape per esportare i livelli ,inkscape -i g1939 -o soldato_frame2.png pedinaSoldatoSkyBase.svg
        comando = f"inkscape {area_param} -j -i {layer_id} -o {output_path}{layer_id}.{filetype} {dest}"
        logging.info("Extension function comando."+comando+" ")
        p = subprocess.Popen(comando , shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        stdout, stderr = p.communicate()
        logging.info(" stdout :"+stdout.decode("utf-8")  )
        logging.info(" stderr :"+stderr.decode("utf-8") )
        p.wait() 


def _main():
    e = PNGPeronalExport()
    e.run()
    exit()
    
if __name__ == "__main__":
    _main()