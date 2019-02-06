package com.example.yellow.controller;

import com.example.yellow.domain.Record;
import com.example.yellow.repository.RecordRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("record")
public class RecordController {
    private final RecordRepository recordRepository;

    @Autowired
    public RecordController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping
    public Iterable<Record> showAll(){
        return recordRepository.findAll();
    }

    @GetMapping("{id}")
    public Record get(@PathVariable("id") Record record){
        return record;
    }

    @PostMapping
    public Record add(@RequestBody Record record){
        return recordRepository.save(record);
    }

    @PutMapping("{id}")
    public Record update(
            @PathVariable("id") Record recordFromDb,
            @RequestBody Record record
    ){
        BeanUtils.copyProperties(record, recordFromDb, "id");

        return recordRepository.save(record);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Record record){
        recordRepository.delete(record);
    }
}
