package br.com.senac.api.services;

import br.com.senac.api.dtos.AnimalRequestDtos;
import br.com.senac.api.entities.Animal;
import br.com.senac.api.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> listar() {
        return animalRepository.findAll();
    }

    public Animal criar (AnimalRequestDtos animal){
        this.validarAnimal(animal);

        Animal animalSaida = new Animal();
        animalSaida.setNome(animal.getNome());
        animalSaida.setEspecie(animal.getEspecie());
        animalSaida.setPeso(animal.getPeso());
        animalSaida.setIdade(animal.getIdade());

        return animalRepository.save(animalSaida);
    }

    public void deletar(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
        animalRepository.delete(animal);
    }

    public Animal listarPorId(Long id){
        Optional<Animal> animalResult = animalRepository.findById(id);

        if (animalResult.isPresent()) {
            return animalResult.get();
        }

        throw new RuntimeException("Animal não Encontrado!");
    }

    public Animal atualizar(Long id, AnimalRequestDtos animal) {
        Optional<Animal> animalResult = animalRepository.findById(id);

        if (animalResult.isPresent()) {
            Animal animalPersist = animalResult.get();
            animalPersist.setNome(animal.getNome());
            animalPersist.setEspecie(animal.getEspecie());
            animalPersist.setIdade(animal.getIdade());
            animalPersist.setPeso(animal.getPeso());
            animalPersist.setId(id);

            return animalRepository.save(animalPersist);
        }

        throw new RuntimeException("Animal não encontrado!");
    }

    private void validarAnimal (AnimalRequestDtos animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()){
            throw new RuntimeException("Campo nome é obrigatório!");
        }

        if (animal.getEspecie() == null || animal.getEspecie().isBlank()) {
            throw new RuntimeException("Campo Espécie é obrigatório!");
        }

        if (animal.getPeso() == null) {
            throw new RuntimeException("Campo peso é Obrigatório");
        }
    }
}
