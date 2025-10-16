package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    private int callsMade = 0;
    BreedFetcher fetcher;
    Map<String, List<String>> map = new HashMap<>();
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        // return statement included so that the starter code can compile and run.
        if (map.containsKey(breed)) {
            return map.get(breed);
        }
        try {
            List<String> returnList = fetcher.getSubBreeds(breed);
            this.callsMade++;
            map.put(breed, returnList);
            return returnList;
        }
        catch(BreedNotFoundException e) {
            this.callsMade++;
            throw new BreedNotFoundException(breed);
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}