package calculapainel;

import br.zul.zwork2.html.ZHtml;
import br.zul.zwork2.html.ZHtmlElement;
import br.zul.zwork2.html.ZHtmlTag;
import br.zul.zwork2.html.ZHtmlValue;
import br.zul.zwork2.io.ZTxtFile;
import br.zul.zwork2.log.ZLogFileWriter;
import br.zul.zwork2.string.ZString;
import br.zul.zwork2.thread.ZThread;
import java.io.IOException;

public class PesoPainel {

    public static void principal(String[] args) {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ZLogFileWriter.setDefaultLogFileWriter(new ZLogFileWriter("Log"));
        String conteudoHtml = new ZTxtFile("C:\\Users\\jeferson.oliveira\\Documents\\NetBeansProjects\\PainelDeVendas\\public_html\\Modelo_Peso.HTML").readAll();
//        ZHtml html = new ZHtml(conteudoHtml);
        String setores[] = {"404", "501", "502", "503", "504", "505", "611", "612", "613", "614", "615", "616"};

        while (1 > 0) {

            String copiaHtml = conteudoHtml;
            String diretorio = "P:\\Descarga\\C0";
            String arquivos[] = {"40404", "50501", "50502", "50503", "50504", "50505", "60611", "60612", "60613", "60614", "60615", "60616"};
            String conteudo = "";
            int i = 0;
            try {
                for (String arquivo : arquivos) {
                    String conteudoArquivo = new ZTxtFile(diretorio + arquivo + ".HTM").readAll();
                    ZString conteudoProcessado = new ZString(conteudoArquivo, false);
                    //Já passaram
                    int passou = conteudoProcessado.count("/HTML>");
                    if (passou > 0) {
                        copiaHtml = copiaHtml.replace("$pass" + setores[i], "&#10004");
                        copiaHtml = copiaHtml.replace("$_pass" + setores[i], "green");

                        //DESC
                        int desc = conteudoProcessado.count("font color=red   >Desc") - conteudoProcessado.count("font color=red   >Desc    , -->Falta");
                        copiaHtml = copiaHtml.replace("$desc" + setores[i], String.valueOf(desc));
                        if (desc > 0) {
                            copiaHtml = copiaHtml.replace("$_desc" + setores[i], "red");
                        } else {
                            copiaHtml = copiaHtml.replace("$_desc" + setores[i], "green");
                        }
                        //FALTANTES
                        int faltante = conteudoProcessado.count("-->Falta");
                        copiaHtml = copiaHtml.replace("$faltante" + setores[i], String.valueOf(faltante));
                        if (faltante > 0) {
                            copiaHtml = copiaHtml.replace("$_faltante" + setores[i], "red");
                        } else {
                            copiaHtml = copiaHtml.replace("$_faltante" + setores[i], "green");
                        }
                        //REJEITADOS
                        int rejeitado = conteudoProcessado.count("    Rejeitado      ");
                        copiaHtml = copiaHtml.replace("$rejeitado" + setores[i], String.valueOf(rejeitado));
                        if (rejeitado > 0) {
                            copiaHtml = copiaHtml.replace("$_rejeitado" + setores[i], "red");
                        } else {
                            copiaHtml = copiaHtml.replace("$_rejeitado" + setores[i], "green");
                        }
                        //ACOB
                        int acob = conteudoProcessado.count("ACOB:");
                        copiaHtml = copiaHtml.replace("$acob" + setores[i], String.valueOf(acob));
                        if (acob > 0) {
                            copiaHtml = copiaHtml.replace("$_acob" + setores[i], "red");
                        } else {
                            copiaHtml = copiaHtml.replace("$_acob" + setores[i], "green");
                        }
                        //TROCAS
                        int troca = conteudoProcessado.count(">TROCA      ");
                        copiaHtml = copiaHtml.replace("$troca" + setores[i], String.valueOf(troca));
                        if (troca > 0) {
                            copiaHtml = copiaHtml.replace("$_troca" + setores[i], "blue");
                        } else {
                            copiaHtml = copiaHtml.replace("$_troca" + setores[i], "green");
                        }
                        //BONIFICAÇÃO
                        int boni = conteudoProcessado.count(">BONIFICACAO");
                        copiaHtml = copiaHtml.replace("$boni" + setores[i], String.valueOf(boni));
                        if (boni > 0) {
                            copiaHtml = copiaHtml.replace("$_boni" + setores[i], "blue");
                        } else {
                            copiaHtml = copiaHtml.replace("$_boni" + setores[i], "green");
                        }
                        //PEDIDOS
                        int qt = conteudoProcessado.count("font color=white>Pedido");
                        copiaHtml = copiaHtml.replace("$qt" + setores[i], String.valueOf(qt));
                        if (qt < 1) {
                            copiaHtml = copiaHtml.replace("$_qt" + setores[i], "black");
                        } else {
                            copiaHtml = copiaHtml.replace("$_qt" + setores[i], "green");
                        }
                        // Status    
                        if ((desc + rejeitado + acob) > 0) {
                            copiaHtml = copiaHtml.replace("$_status" + setores[i], "red");
                        } else {
                            copiaHtml = copiaHtml.replace("$_status" + setores[i], "green");
                        }
                        //PEDIDOS Fora de Rota
                        int fora = conteudoProcessado.count("Fora de Rota");
                        copiaHtml = copiaHtml.replace("$fora" + setores[i], String.valueOf(fora));
                        if (fora > 0) {
                            copiaHtml = copiaHtml.replace("$_fora" + setores[i], "blue");
                        } else {
                            copiaHtml = copiaHtml.replace("$_fora" + setores[i], "green");
                        }
//Resultado
                        ZString conteudo1;
                        conteudo1 = conteudoProcessado;
                        float resultadoFinal = 0;
                        float resultado = 0;
                        while (conteudo1.contains("(Kg):")) {
                            conteudo1 = conteudo1.fromLeft("(Kg):");
                            float valor;
                            valor = conteudo1.toLeft("</font></TD></tr>").trim().replace(" ", "").replace(",", ".").asFloat();
                            resultadoFinal += valor;                           
                        }
                        copiaHtml = copiaHtml.replace("$tt" + setores[i], String.valueOf(resultadoFinal));
                        if (resultado < 1) {
                            copiaHtml = copiaHtml.replace("$_tt" + setores[i], "black");
                        } else {
                            copiaHtml = copiaHtml.replace("$_tt" + setores[i], "green");
                        }
// ----> A continuar  <-----                      System.out.println("Setor " + setores[i] + ", resultado:" + resultadoFinal);

                    } else {
// SE NÃO
                        copiaHtml = copiaHtml.replace("$pass" + setores[i], "&#128078");
                        copiaHtml = copiaHtml.replace("$_pass" + setores[i], "red");
                        //Desc
                        int desc = 0;
                        copiaHtml = copiaHtml.replace("$desc" + setores[i], String.valueOf(desc));
                        copiaHtml = copiaHtml.replace("$_desc" + setores[i], "black");
                        //Faltante
                        int faltante = 0;
                        copiaHtml = copiaHtml.replace("$faltante" + setores[i], String.valueOf(faltante));
                        copiaHtml = copiaHtml.replace("$_faltante" + setores[i], "black");
                        //Rejeitado
                        int rejeitado = 0;
                        copiaHtml = copiaHtml.replace("$rejeitado" + setores[i], String.valueOf(rejeitado));
                        copiaHtml = copiaHtml.replace("$_rejeitado" + setores[i], "black");
                        //Acob
                        int acob = 0;
                        copiaHtml = copiaHtml.replace("$acob" + setores[i], String.valueOf(acob));
                        copiaHtml = copiaHtml.replace("$_acob" + setores[i], "black");
                        //Troca
                        int troca = 0;
                        copiaHtml = copiaHtml.replace("$troca" + setores[i], String.valueOf(troca));
                        copiaHtml = copiaHtml.replace("$_troca" + setores[i], "black");
                        //Boni
                        int boni = 0;
                        copiaHtml = copiaHtml.replace("$boni" + setores[i], String.valueOf(boni));
                        copiaHtml = copiaHtml.replace("$_boni" + setores[i], "black");
                        //PEDIDOS
                        int qt = 0;
                        copiaHtml = copiaHtml.replace("$qt" + setores[i], String.valueOf(qt));
                        copiaHtml = copiaHtml.replace("$_qt" + setores[i], "black");
                        //PEDIDOS Fora
                        int fora = 0;
                        copiaHtml = copiaHtml.replace("$fora" + setores[i], String.valueOf(fora));
                        copiaHtml = copiaHtml.replace("$_fora" + setores[i], "black");
                        
                        int resultadoFinal = 0;
                        copiaHtml = copiaHtml.replace("$tt" + setores[i], String.valueOf(fora));
                        copiaHtml = copiaHtml.replace("$_tt" + setores[i], "black");

                        //Status
//                    copiaHtml = copiaHtml.replace("$_status" + setores[i], "black");
                    }

                    i++;
                }

            } catch (Exception ex) {
            }
//            System.out.println("_________");
            new ZTxtFile("C:\\Painel\\Pesos.HTML").writeAll(copiaHtml);
            ZThread.sleep(5000);
//            System.out.print(" . ");

        }
    }

    public static void setValue(ZHtml html, String idTag, String novoValor) {
        ZHtmlTag tag = html.findsFirstTag("#" + idTag);
        for (ZHtmlElement element : tag.listElements()) {
            tag.removeElement(element);
        }
        tag.addElement(new ZHtmlValue(novoValor));
    }

}
