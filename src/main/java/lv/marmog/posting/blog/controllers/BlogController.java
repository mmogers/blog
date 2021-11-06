package lv.marmog.posting.blog.controllers;


import lv.marmog.posting.blog.models.Post;
import lv.marmog.posting.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired //Autowired neobhodimo dlya sozdaniya peremennoj kotoraya ssylaetsya na repositorij
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain (Model model){
        Iterable<Post> posts = postRepository.findAll(); //massiv dannyh so vsemi znacheniyami poluchennymi iz tablichki v bd. findall vytaschit vse zapisi
        model.addAttribute("posts", posts); //vse zapisi peredaem v shablon blog-main
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd (Model model){
        return "blog-add";
    }

    @PostMapping("/blog/add") //iz formy blogg-add poluchaem dannye s pomoschjumethoda post
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
       /* neobhodimo poluchat'dannye iz formy - title, anons, full_text, imya takoe zha kak v forme*/
        Post post = new Post(title, anons, full_text); // sozdayom object i v nego peredayom dannye
        //teper'my dolzhny peredat'dannye v bd
        postRepository.save(post); // teper'v tablichku dobavlyaetsya novaya stat'ya
        return "redirect:/blog";
    }

    //nabo otslezhivat'blog/2 ili blog/4 dinamicheski, stranichka otkryvaetsya posle togo kak nazhali more details i otobrazhaet full text of specific id
    @GetMapping("/blog/{id}") // mozhet byt'("/blog/{id}/{author}...")
    public String blogDetails (@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id); //optional rabotaet s etim objectom v etot object zagruzhaem dannye odnoj etoj zapisi
        ArrayList<Post> res = new ArrayList<>();  //optional vnutr'shablona trudno rabotat'', luchshe object classa optional perevesti v object classa arraylist
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details"; //perehodim v details
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit (@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        Post post = postRepository.findById(id).orElseThrow(); //vybrasyvaet iskluchenie esli zapis'ne najdena
        post.setTitle(title); // to chto vvedet user, no ne sozdajem novyj object a obnovlyaem suschestvujuschij
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostRemove(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow(); //vybrasyvaet iskluchenie esli zapis'ne najdena
        postRepository.delete(post); // udalyaem post kotoryj nashli
        return "redirect:/blog";
    }

}
