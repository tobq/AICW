package com.tobi;

import java.util.HashSet;

/**
 * The Traversal class is used to keep track of all
 * previous branch traversals, so the algorithm can
 * skip already traversed branches.
 *
 * @see State#search()
 * @see HashSet
 */

class Traversal {
    private final int branch;
    private final State state;

    Traversal(State state, int branch) {
        this.branch = branch;
        this.state = state;
    }

    /**
     * @return String representation for this traversal, used when logging in console
     */

    @Override
    public String toString() {
        return branch + ": " + state.toString();
    }


    /**
     * @param obj State to be compared to
     * @return this and obj are the same traversal
     */

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Traversal) {
            Traversal traversal = (Traversal) obj;
            return traversal.branch == branch &&
                    traversal.state.equals(state);
        } else return false;
    }

    /**
     * used by HashSet class, to assign a location
     * for object in the HashTable
     *
     * @return Hash representation of this state
     * @see HashSet
     * @see StateSearch#pair(int...)
     */

    @Override
    public int hashCode() {
        return StateSearch.pair(branch, state.hashCode());
    }
}
