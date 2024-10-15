    package org.ejercicio.cosas;

    import javax.xml.bind.JAXBContext;
    import javax.xml.bind.JAXBException;
    import javax.xml.bind.Marshaller;
    import javax.xml.bind.Unmarshaller;
    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Scanner;
    import com.fasterxml.jackson.databind.ObjectMapper;


    public class FeriaApp {

        private static final String TEXT_FILE_PATH = "/home/tarde/IdeaProjects/Casetas/src/main/java/org/ejercicio/cosas/casetas.txt";
        private static final String XML_FILE_PATH = "casetas.xml";
        private static final String JSON_FILE_PATH = "casetas.json";
        private static List<CasetaFeria> casetaFeriaList = new ArrayList<>();

        public static void main(String[] args) {
            cargarCasetasDesdeTexto();

            CasetaFeriaList feria = new CasetaFeriaList();
            feria.setCasetas(casetaFeriaList);
            Scanner scanner = new Scanner(System.in);
            int opcion;

            do {
                System.out.println("Menú:");
                System.out.println("1. Marshalling casetas a XML");
                System.out.println("2. Unmarshalling casetas de XML");
                System.out.println("3. Mostrar la caseta número X");
                System.out.println("4. Marshalling casetas a JSON");
                System.out.println("5. Unmarshalling casetas de JSON");
                System.out.println("6. Mostrar la caseta número X desde JSON");
                System.out.println("7. Salir");
                System.out.print("Elija una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        marshallingCasetasA_XML(feria);
                        break;
                    case 2:
                        unmarshallingCasetasDe_XML();
                        break;
                    case 3:
                        mostrarCasetaPorID(scanner);
                        break;
                    case 4:
                        marshallingCasetasA_JSON();
                        break;
                    case 5:
                        unmarshallingCasetasDe_JSON();
                        break;
                    case 6:
                        mostrarCasetaDesdeJSON(scanner);
                        break;
                    case 7:
                        System.out.println("Saliendo de la aplicación...");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } while (opcion != 7);
            scanner.close();
        }

        private static void cargarCasetasDesdeTexto() {
            try (BufferedReader br = new BufferedReader(new FileReader(TEXT_FILE_PATH))) {
                String line;
                int id = 1; // Asignar un ID incremental a cada caseta
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    if (parts.length == 4) {
                        String nombre = parts[0].trim();
                        String titular = parts[1].trim();
                        int aforo = Integer.parseInt(parts[2].trim());
                        String tipoCaseta = parts[3].trim();

                        CasetaFeria casetaFeria = new CasetaFeria(id++, nombre, titular, aforo, tipoCaseta);
                        casetaFeriaList.add(casetaFeria);
                    }
                }
                //System.out.println(casetaFeriaList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void marshallingCasetasA_XML(CasetaFeriaList feria) {

            try {
                // Crear contexto JAXB para la clase CasetaFeriaList
                JAXBContext jaxbContext = JAXBContext.newInstance(CasetaFeriaList.class);

                // Crear el Marshaller
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                // Serializar la lista de casetas a XML
                marshaller.marshal(feria, new File(XML_FILE_PATH));

                System.out.println("Casetas serializadas a XML en " + XML_FILE_PATH);
            } catch (JAXBException e) {
                System.out.println("Error durante el marshalling a XML: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private static void unmarshallingCasetasDe_XML() {
            File file = new File(XML_FILE_PATH);
            if (!file.exists()) {
                System.out.println("El archivo XML no existe.");
                return;
            }
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(CasetaFeriaList.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                CasetaFeriaList casetaFeriaWrapper = (CasetaFeriaList) unmarshaller.unmarshal(file);
//                casetaFeriaList = casetaFeriaWrapper.getCasetas();

                System.out.println("Casetas deserializadas de XML:");
                casetaFeriaList.forEach(System.out::println);
            } catch (JAXBException e) {
                System.out.println("Error durante el unmarshalling desde XML: " + e.getMessage());
                e.printStackTrace();
            }
        }

        private static void mostrarCasetaPorID(Scanner scanner) {
            System.out.print("Introduzca el ID de la caseta: ");
            int id = scanner.nextInt();
            for (CasetaFeria caseta : casetaFeriaList) {
                if (caseta.getId() == id) {
                    System.out.println("Caseta encontrada: " + caseta);
                    return;
                }
            }
            System.out.println("No se encontró una caseta con ese ID.");
        }

        private static void marshallingCasetasA_JSON() {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(new File(JSON_FILE_PATH), casetaFeriaList);
                System.out.println("Casetas serializadas a JSON en " + JSON_FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void unmarshallingCasetasDe_JSON() {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                CasetaFeria[] casetas = objectMapper.readValue(new File(JSON_FILE_PATH), CasetaFeria[].class);
                for (CasetaFeria caseta : casetas) {
                    System.out.println(caseta);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void mostrarCasetaDesdeJSON(Scanner scanner) {
            System.out.print("Introduzca el ID de la caseta: ");
            int id = scanner.nextInt();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                CasetaFeria[] casetas = objectMapper.readValue(new File(JSON_FILE_PATH), CasetaFeria[].class);
                for (CasetaFeria caseta : casetas) {
                    if (caseta.getId() == id) {
                        System.out.println("Caseta encontrada: " + caseta);
                        return;
                    }
                }
                System.out.println("No se encontró una caseta con ese ID.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
