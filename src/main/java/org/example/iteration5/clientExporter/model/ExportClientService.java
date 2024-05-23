package org.example.iteration5.clientExporter.model;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.utils.MyUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ExportClientService {
    @Getter
    private List<Contact> contactList;
    private final static int NUM_CONT = 1000;

    @Getter
    private String pathExportCSV;
    @Getter
    private final FileWriter fw;
    private final BufferedWriter writer;

    @SneakyThrows
    public ExportClientService(@Value("${csv.default-export-path}") String pathExportCSV) {
        this.pathExportCSV = pathExportCSV;
        this.fw = new FileWriter(pathExportCSV);
        this.writer = new BufferedWriter(new FileWriter("src/main/java/org/example/iteration5/clientExporter/result/resultBuffer.txt"));
//        fw.write("sdsasadsad\n");
//        fw.write("annabana\n");
//        fw.flush();
//        int b = 4;
    }

    public @NotNull List<Contact> queryContactList() {
        if(Optional.ofNullable(contactList).isEmpty()){
            List<Contact> contacts = new ArrayList<>();
            for (int i = 0; i < NUM_CONT ; i++) {
                contacts.add(new Contact((long) i, "Name_" + i, "LastName__" + i, "email_" + i + "@mail.com"));
            }
            contactList = contacts;
            log.info("Получены контакты");
        }
        return contactList;
    }
//    public void writeCSVClient(List<Contact> contacts){
//        contacts.stream().map().forEach();
//    }
    @Async
    public void writeContacts(List<Contact> list) {
        list.forEach(contact -> {
//            MyUtils.sleep(1000);
            try {
                writer.write(contact.toString());
                fw.write(contact.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MyUtils.clog(contact.toString());
        });
    }
}
