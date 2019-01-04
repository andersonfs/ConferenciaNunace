package br.com.m4u.multirecargas;

import br.com.m4u.multirecargas.builder.ChannelConfigBuilder;
import br.com.m4u.multirecargas.entity.Channel;
import br.com.m4u.multirecargas.service.ComparisonService;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println(printMenu());
        Scanner input = new Scanner(System.in);
        Integer option = input.nextInt();
        Map<String, Channel> configurationMap = null;
        while (option != 3) {

            switch (option) {
                case 1:
                    configurationMap = handleConfigurationFile();
                    option = 0;
                    break;
                case 2:
                    option = initMatch(configurationMap);
                    break;
                default:
                    System.out.println(printMenu());
                    input = new Scanner(System.in);
                    option = input.nextInt();
            }
        }
        System.out.println("Programa finalizado");

    }

    private static Integer initMatch(final Map<String, Channel> configurationMap) {
        System.out.println("Iniciando comparacao...");
        System.out.println("Informe o nome do arquivo com o path completo:");
        Scanner input = new Scanner(System.in);
        String fileName = input.next();
        System.out.println("Informe o caminho para os aquivos de resultado:");
        input = new Scanner(System.in);
        String outputPath = input.next();
        final ComparisonService service = new ComparisonService();
        final Integer result = service.init(configurationMap, fileName, outputPath);
        System.out.println("Comparacao finalizada");
        return result;
    }

    private static Map<String, Channel> handleConfigurationFile() {
        System.out.println("Informe o caminho do arquivo de configuracao:");
        Scanner input = new Scanner(System.in);
        String fileName = input.next();
        final ChannelConfigBuilder builder = new ChannelConfigBuilder();
        builder.build(fileName);
        System.out.println("Leitura do arquivo de configuração finalizada");
        return builder.getChannels();

    }

    private static String printMenu() {
        final StringBuilder menu = new StringBuilder();
        menu.append("===============MENU==================\n");
        menu.append("1 - Carregar Arquivo de Configuracao\n");
        menu.append("2 - Iniciar Conferencia\n");
        menu.append("3 - Sair\n");
        menu.append("=====================================\n");
        return menu.toString();
    }
}
