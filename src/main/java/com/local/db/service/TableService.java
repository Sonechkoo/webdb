package com.local.db.service;

import com.local.db.model.*;
import com.local.db.repository.AttributeRepository;
import com.local.db.repository.RowRepository;
import com.local.db.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private RowService rowService;

    public Table findByName(Base base, String name){
        Table table = tableRepository.findByBaseAndNameIgnoreCase(base, name);

        if (table == null) {
            throw new RuntimeException("Table Not Found!");
        }
        return table;
    }

    public void addTable(Table table, List<String> columns, List<Type> types) throws Exception {
        Table dbTable = tableRepository.findByBaseAndNameIgnoreCase(table.getBase(), table.getName());
        if (dbTable != null)
            throw new Exception("Table with the same name exists!");

        if(columns.size() != types.size())
            throw new Exception("Error in attributes!");

        List<Attribute> attributes = convertToAttributes(table, columns, types);

        if(!checkAttributes(attributes))
            throw new Exception("Error in attributes!");

        table.setAttributes(attributes);

        tableRepository.save(table);
    }

    public void removeTable(Table table) {
        tableRepository.delete(table);
    }

    public void sortTableRows(Table table, String sortParam) {
        int columnIndex = 0;
        Type type = Type.STRING;
        for(Attribute attribute : table.getAttributes()) {
            type = attribute.type;
            if(attribute.name.equals(sortParam)) break;
            columnIndex++;
        }
        List data;
        List<Row> rows = table.getRows();
        switch (type) {
            case INTEGER: {
                data = new ArrayList<Integer>();
                for(Row row : rows){
                    data.add(Integer.parseInt(row.values.get(columnIndex)));
                }
                break;
            }
            case REAL: {
                data = new ArrayList<Double>();
                for(Row row : rows){
                    data.add(Double.parseDouble(row.values.get(columnIndex)));
                }
                break;
            }
            default: {
                data = new ArrayList<String>();
                for(Row row : rows){
                    data.add(row.values.get(columnIndex));
                }
            }
        }

        Collections.sort(data);
        rowService.removeRows();
        for(Object elem : data) {
            for(Row row : rows){
                if(row.values.get(columnIndex).equals(elem.toString())) {
                    rowService.addRow(row);
                }
            }
        }
    }

    private List<Attribute> convertToAttributes(Table table, List<String> columns, List<Type> types) {
        List<Attribute> attributes = new ArrayList<>();
        for(int i = 0; i < columns.size(); i++){
            Attribute attribute = new Attribute();
            String name = columns.get(i);
            Type type = types.get(i);

            attribute.setName(name);
            attribute.setType(type);
            attribute.setTable(table);
            attributes.add(attribute);
        }
        return attributes;
    }

    private boolean checkAttributes(List<Attribute> attributes){
        for(Attribute attribute : attributes){
            if(attribute.getName().trim().equals("") || attribute.getName().length() > 20 || attribute.getType() == null)
                return false;
        }
        return true;
    }

}
